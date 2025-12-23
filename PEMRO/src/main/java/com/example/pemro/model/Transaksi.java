package com.example.pemro.model;

import java.sql.Date;
import java.time.LocalDate;

public class Transaksi {
    private int id;
    private int postinganId;
    private int peminatId;
    private String status;
    private Date tanggal;

    public Transaksi(int id, int postinganId, int peminatId, String status, Date tanggal) {
        this.id = id;
        this.postinganId = postinganId;
        this.peminatId = peminatId;
        this.status = status;
        this.tanggal = tanggal;
    }

    public Transaksi(int postinganId, int peminatId) {
        this.postinganId = postinganId;
        this.peminatId = peminatId;
        this.status = "PENDING";
        this.tanggal = Date.valueOf(LocalDate.now());
    }

    public int getId() { return id; }
    public int getPostinganId() { return postinganId; }
    public int getPeminatId() { return peminatId; }
    public String getStatus() { return status; }
    public Date getTanggal() { return tanggal; }
}