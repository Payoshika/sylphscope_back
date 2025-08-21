package com.scholarship.scholarship.dto;

public class SwitchManagerRequest {
    private String managerId;
    private String otherStaffId;

    public String getManagerId() {
        return managerId;
    }
    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
    public String getOtherStaffId() {
        return otherStaffId;
    }
    public void setOtherStaffId(String otherStaffId) {
        this.otherStaffId = otherStaffId;
    }
}

