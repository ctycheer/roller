/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 */

package org.apache.roller.weblogger.ui.struts2.editor;

import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.roller.weblogger.business.WebloggerFactory;
import org.apache.roller.weblogger.business.WeblogEntryManager;
import org.apache.roller.weblogger.pojos.WeblogCategory;
import org.apache.roller.weblogger.pojos.WeblogPermission;
import org.apache.roller.weblogger.ui.struts2.util.UIAction;
import org.apache.roller.weblogger.util.cache.CacheManager;
import org.apache.struts2.interceptor.validation.SkipValidation;


/**
 * Add a new WeblogCategory to the weblog
 */
public class CategoryAdd extends UIAction {
    
    private static Log log = LogFactory.getLog(CategoryAdd.class);
    
    // bean for managing form data
    private CategoryBean bean = new CategoryBean();

    public CategoryAdd() {
        this.actionName = "categoryAdd";
        this.desiredMenu = "editor";
        this.pageTitle = "categoryForm.add.title";
    }

    // author perms required
    public List<String> requiredWeblogPermissionActions() {
        return Collections.singletonList(WeblogPermission.ADMIN);
    }

    /**
     * Show category form.
     */
    @SkipValidation
    public String execute() {
        return INPUT;
    }

    
    /**
     * Save new category.
     */
    public String save() {
        // validation
        myValidate();
        
        if(!hasActionErrors()) {
            try {

                WeblogCategory newCategory = new WeblogCategory(
                        getActionWeblog(),
                        getBean().getName(),
                        getBean().getDescription(),
                        getBean().getImage());

                // save changes
                WeblogEntryManager wmgr = WebloggerFactory.getWeblogger().getWeblogEntryManager();
                wmgr.saveWeblogCategory(newCategory);
                WebloggerFactory.getWeblogger().flush();

                // notify caches
                CacheManager.invalidate(newCategory);

                // TODO: i18n
                addMessage("category added");

                return SUCCESS;

            } catch(Exception ex) {
                log.error("Error saving new category", ex);
                // TODO: i18n
                addError("Error saving new category");
            }
        }
        
        return INPUT;
    }

    public void myValidate() {
        // TODO: Check max length & no html

        // make sure new name is not a duplicate of an existing folder
        if(getActionWeblog().hasCategory(getBean().getName())) {
            addError("categoryForm.error.duplicateName", getBean().getName());
        }
    }

    public CategoryBean getBean() {
        return bean;
    }

    public void setBean(CategoryBean bean) {
        this.bean = bean;
    }
    
}
