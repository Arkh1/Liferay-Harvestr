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

package com.solutiondesign.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.solutiondesign.service.FeedServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.solutiondesign.service.FeedServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.solutiondesign.model.FeedSoap}.
 * If the method in the service utility returns a
 * {@link com.solutiondesign.model.Feed}, that is translated to a
 * {@link com.solutiondesign.model.FeedSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Bryan Smith
 * @see FeedServiceHttp
 * @see com.solutiondesign.model.FeedSoap
 * @see com.solutiondesign.service.FeedServiceUtil
 * @generated
 */
public class FeedServiceSoap {
	public static com.solutiondesign.model.FeedSoap[] myFeeds()
		throws RemoteException {
		try {
			java.util.List<com.solutiondesign.model.Feed> returnValue = FeedServiceUtil.myFeeds();

			return com.solutiondesign.model.FeedSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.solutiondesign.model.FeedSoap addFeedJson(
		java.lang.String json) throws RemoteException {
		try {
			com.solutiondesign.model.Feed returnValue = FeedServiceUtil.addFeedJson(json);

			return com.solutiondesign.model.FeedSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.solutiondesign.model.FeedSoap addFeed(
		java.lang.String url) throws RemoteException {
		try {
			com.solutiondesign.model.Feed returnValue = FeedServiceUtil.addFeed(url);

			return com.solutiondesign.model.FeedSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.solutiondesign.model.FeedSoap updateFeed(long feedId,
		java.lang.String url) throws RemoteException {
		try {
			com.solutiondesign.model.Feed returnValue = FeedServiceUtil.updateFeed(feedId,
					url);

			return com.solutiondesign.model.FeedSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.solutiondesign.model.FeedSoap deleteFeed(long feedId)
		throws RemoteException {
		try {
			com.solutiondesign.model.Feed returnValue = FeedServiceUtil.deleteFeed(feedId);

			return com.solutiondesign.model.FeedSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(FeedServiceSoap.class);
}