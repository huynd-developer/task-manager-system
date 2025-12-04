package Entity;

public class Student {
    private String id;
    private String name;
    private int tuoi = 0;
    private Float avgMark = 0.0F;
    private int kiHoc;
    private String chuyenNganh;

    public Student(String name, String id, int tuoi, Float avgMark, int kiHoc, String chuyenNganh) {
        this.name = name;
        this.id = id;
        this.tuoi = tuoi;
        this.avgMark = avgMark;
        this.kiHoc = kiHoc;
        this.chuyenNganh = chuyenNganh;
    }

    public Student() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public Float getAvgMark() {
        return avgMark;
    }

    public void setAvgMark(Float avgMark) {
        this.avgMark = avgMark;
    }

    public int getKiHoc() {
        return kiHoc;
    }

    public void setKiHoc(int kiHoc) {
        this.kiHoc = kiHoc;
    }

    public String getChuyenNganh() {
        return chuyenNganh;
    }

    public void setChuyenNganh(String chuyenNganh) {
        this.chuyenNganh = chuyenNganh;
    }
}
