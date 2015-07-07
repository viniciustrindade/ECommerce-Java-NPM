/*
 * Copyright 2015 AppDynamics, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdynamicspilot.oracle.jdbc;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * 
 * @author Sharat Jagannath
 *
 */
public abstract class OracleQueryExecutor {
    private final static Logger LOGGER = Logger.getLogger(OracleQueryExecutor.class.getName());
    private String oracleQueryString;
    private DataSource ds  = null;



    public abstract void  executeOracleQuery();
    
    public void setOracleQueryString(String oracleQueryString) {
        this.oracleQueryString = oracleQueryString;
    }

    public String getOracleQueryString() {
        return oracleQueryString;
    }
    
    public void setDataSource(DataSource dataSource) {
       this.ds = dataSource;
    }
    public DataSource getDataSource() {
        return this.ds;
    }

};
