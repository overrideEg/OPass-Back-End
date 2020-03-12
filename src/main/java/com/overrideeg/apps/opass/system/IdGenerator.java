package com.overrideeg.apps.opass.system;


import com.overrideeg.apps.opass.exceptions.CouldNotCreateRecordException;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IdGenerator implements IdentifierGenerator {
    /**
     * Generate a new identifier.
     *
     * @param session The session from which the request originates
     * @param object  the entity or collection (idbag) for which the id is being generated
     * @return a new identifier
     * @throws HibernateException Indicates trouble generating the identifier
     */
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Connection connection = session.connection();
        StringBuilder query = new StringBuilder();
        query.append("select max(id) as id ").append("from").append(" ");
        query.append(object.getClass().getSimpleName());
        try {
            Statement statement=connection.createStatement();
            Long prev = (Long) object.getClass().getMethod("getId").invoke(object);
            if (prev != null) {
                ResultSet rs ;
                try{
                    rs = statement.executeQuery("select id as id from" + " " + object.getClass().getSimpleName() +" where id =" +prev);
                    if (rs.next()) {
                        ResultSet resultSet = statement.executeQuery(query.toString());
                        if (resultSet.next()) {
                            return resultSet.getLong(1) + 1;
                        }
                    } else {
                        return prev;
                    }
                } catch (SQLException e){
                    return prev;
                }
            }
            else {
                ResultSet rs = statement.executeQuery(query.toString());
                if (rs.next()) {
                    return rs.getLong(1) + 1;
                }
            }
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new CouldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage()+ e.getMessage());
        }
        return null;
    }
}
