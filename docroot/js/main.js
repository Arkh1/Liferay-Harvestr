;var SDG = (function(me){
	/*
	 * @param A: AlloyUI instantiated with aui-tabview, aui-datatable,
	 * 	aui-datatype, datatable-sort, and datatable-mutable
	 */
	me.RssPortlet = function(A){
		var items = [], 
			source = $("#feed-item-template").html(),
			template = Handlebars.compile(source),
			dataTable;		
		
		/*
		 * Setup tab view
		 */
		new A.TabView({
			srcNode : '#myTab',
			type: 'pills'
		}).render();
		
		A.one("#myDataTable").delegate('click', onDelete, '.removeFeed');
		A.one("html").delegate('click', onAllowUserFeeds, '.allowUserFeedsBtn');
		
		function onAllowUserFeeds(event) {
			event.preventDefault();
			var url = event.currentTarget.getAttribute("data-url");
			var button=A.one('.allowUserFeedsBtn');
			var originalLabel = button.html();
			button.html("Wait...");
			button.set('disabled',true);
			A.io.request(url, {
				   method: 'GET',
				   dataType: 'json',
				   on: { 
					   success: function() { 						 
						   button.html(this.get('responseData'));
						   button.set('disabled',false);
					   },
					   failure: function() { alert("There was a problem serving your request")
						   button.set('disabled',false);
						   button.html(originalLabel);
					   }
				   }
				});
		}
		
		function onDelete(event) {
			event.preventDefault();			
			var feedId=event.currentTarget.getAttribute("data-feedId");
			if (confirm ("Are you sure?")) {
				Liferay.Service(
						  '/RSS-portlet.feed/delete-feed',
						  {
						    feedId: feedId
						  },
						  function(obj) {
						    var record = dataTable.getRecord(event.currentTarget.get('id'));
						    dataTable.removeRow(record);
						  }
						);
			}

		}
		/*
		 * Get the Feed URL's, populate the 'Feeds' tab using Google's RSS API,
		 * and populate the 'Edit' tab.
		 */
		Liferay.Service('/RSS-portlet.feed/my-feeds',
			function(feeds) {
				/*
				 *  Return if user doesn't have any feeds setup
				 *  TODO: Handle this in a better way
				 */
				if (!_.isArray(feeds)) return;
				
				_.each(feeds,
					function(feed) {
						$.ajax({
							url : "http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&num="+ 3+ "&output=json&q="+ feed.url+ "&hl=en&callback=?",
							dataType : "json",
							success : function(data) {
								/*
								 * Google can't get the RSS feed
								 */
								if (data.responseStatus == 400) return;
								
								/*
								 * Union new feed items with previous feed items and order by
								 * the publish date, descending
								 */ 
								items = _.sortBy(
									_.union(items,data.responseData.feed.entries),
									function(item) {return moment(item.publishedDate).format();}
								).reverse();

								/*
								 * Empty any previous items because we have a superset of that data
								 */
								$('#feedList').empty();

								/*
								 * Use Handlebars template to render and append on the list
								 */
								_.each(items,
									function(item) {
										item.publishedDate = moment(item.publishedDate).fromNow();
										$('#feedList').append(template(item));
								});
							}
						});
				});
				
				/*
				 * Setup data table
				 */
				dataTable = new A.DataTable({
					columns : [{
						editor : new A.TextAreaCellEditor({
							validator : {
								rules : {
									url : {
										required : true
									}
								}
							}
						}),
						key : 'url',
						sortable : true
					},
					{
						key:'feedId',
						label: ' ',
						formatter: '<a href="#" class="removeFeed" data-feedId="{value}"><i class="fa fa-trash-o fa-fw"></i></a>',
						allowHTML: true
					},
                    {
						key:'edit',
                        label:' ',
                        className:'edit-button',
                        allowHTML: true,
                        formatter: '<a href="#" data-feedId="{value}"><i class="fa fa-pencil fa-fw"></i></a>'
                	}],
					data : feeds,
					editEvent : 'click'
				});
				
				dataTable.render('#myDataTable');
				
				/*
				 * On URL Edit, lookup the feed, and update the DB
				 */
				dataTable.subscribe("model:urlChange", function(e){
					var prevUrlFeed = _.find(feeds, function(feed){
						return feed.url == e.prevVal;
					});

					/*
					 * TODO: Add better error handling
					 */
					if (_.isUndefined(prevUrlFeed)) return;
				
					Liferay.Service('/RSS-portlet.feed/update-feed',
						{  	feedId: prevUrlFeed.feedId,
					    	url: e.newVal	},
				    	function(obj) {
				    		console.log(obj);
				    	}
					);
				});
			});
	};
	
	return me;
}(SDG || {}));

/*
 * Instantiate the RssPortlet
 */
AUI().use(
	'aui-tabview',
	'aui-datatable',
	'aui-datatype',
	'datatable-sort',
	'datatable-mutable',
	function(A) {
		new SDG.RssPortlet(A);
});