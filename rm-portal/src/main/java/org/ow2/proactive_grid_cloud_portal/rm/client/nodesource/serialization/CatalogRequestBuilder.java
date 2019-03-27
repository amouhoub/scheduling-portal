/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive_grid_cloud_portal.rm.client.nodesource.serialization;

import org.ow2.proactive_grid_cloud_portal.common.client.model.LoginModel;
import org.ow2.proactive_grid_cloud_portal.rm.shared.CatalogConstants;
import org.ow2.proactive_grid_cloud_portal.rm.shared.CatalogKind;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;


public class CatalogRequestBuilder {

    private String catalogUrl;

    public CatalogRequestBuilder() {
        this.catalogUrl = new CatalogUrlRmClientBuilder().getCatalogUrl();
    }

    public void sendRequestToCatalog(String endpoint, RequestCallback callback) {
        RequestBuilder request = new RequestBuilder(RequestBuilder.GET, this.catalogUrl + "/" + endpoint);
        request.setHeader(CatalogConstants.SESSION_ID_PARAM, LoginModel.getInstance().getSessionId());
        request.setCallback(callback);
        try {
            request.send();
        } catch (RequestException e) {
            throw new IllegalStateException("Request sent to catalog failed", e);
        }
    }

    public void requestCatalogObjects(String bucketName, CatalogKind kind, RequestCallback callback) {
        sendRequestToCatalog("buckets/" + bucketName + "/resources?" + CatalogConstants.KIND_PARAM + "=" +
                             kind.getKindString(), callback);
    }

}
