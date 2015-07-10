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

package com.appdynamics.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import com.appdynamics.inventory.QueryExecutor;

/**
 *
 * @author Owner
 */
public class MStatement implements Statement{

    public ResultSet executeQuery(String sql) throws SQLException {
        try{
            Thread.sleep(QueryExecutor.SLEEP_TIME);
        }catch(InterruptedException ie){

        }
        return new MResultSet();
    }

    public int executeUpdate(String sql) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void close() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getMaxFieldSize() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setMaxFieldSize(int max) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getMaxRows() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setMaxRows(int max) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setEscapeProcessing(boolean enable) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getQueryTimeout() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setQueryTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void cancel() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SQLWarning getWarnings() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clearWarnings() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCursorName(String name) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean execute(String sql) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResultSet getResultSet() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getUpdateCount() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean getMoreResults() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setFetchDirection(int direction) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getFetchDirection() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setFetchSize(int rows) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getFetchSize() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getResultSetConcurrency() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getResultSetType() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addBatch(String sql) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clearBatch() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int[] executeBatch() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Connection getConnection() throws SQLException {
        return new MConnection();
    }

    public boolean getMoreResults(int current) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean execute(String sql, String[] columnNames) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getResultSetHoldability() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isClosed() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPoolable(boolean poolable) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isPoolable() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}