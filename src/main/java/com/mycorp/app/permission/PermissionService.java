package com.mycorp.app.permission;

import java.sql.SQLException;
import java.util.List;

public interface PermissionService {
    List<Permission> fetchPermissions() throws SQLException;
    Permission fetchSinglePermission(int id) throws SQLException;
}
