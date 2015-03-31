package net.sebinson.sample.web.mis.persistence.domain;

public class SampleUserRole extends SampleUserRoleKey {
    private String remarks;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}