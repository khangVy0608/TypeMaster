
package org.SpecikMan.Entity;

public class Role {
    private String idRole;
    private String nameRole;
    //region get-set
    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }
    //endregion
    //region Constructor
    public Role() {
    }

    public Role(String idRole, String nameRole) {
        this.idRole = idRole;
        this.nameRole = nameRole;
    }//endregion
}
