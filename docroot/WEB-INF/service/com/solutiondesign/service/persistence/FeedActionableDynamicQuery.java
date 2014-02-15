/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.solutiondesign.service.persistence;

import com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.solutiondesign.model.Feed;

import com.solutiondesign.service.FeedLocalServiceUtil;

/**
 * @author Bryan Smith
 * @generated
 */
public abstract class FeedActionableDynamicQuery
	extends BaseActionableDynamicQuery {
	public FeedActionableDynamicQuery() throws SystemException {
		setBaseLocalService(FeedLocalServiceUtil.getService());
		setClass(Feed.class);

		setClassLoader(com.solutiondesign.service.ClpSerializer.class.getClassLoader());

		setPrimaryKeyPropertyName("feedId");
	}
}