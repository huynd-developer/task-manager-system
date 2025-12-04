package Service;

import Entity.Student;

public class StudentService {
    protected Student student = new Student("TH04383","Nguyễn Đình Huy",18,8.5F,6,"CNTT");
    public void updateStudent(Student newStudent){
        if (newStudent == null){
            throw new IllegalArgumentException("Đối tượng sinh viên không được rỗng");
        }
        if (newStudent.getId() == null || newStudent.getId().isEmpty() || newStudent.getName() == null || newStudent.getName().isEmpty()|| newStudent.getChuyenNganh() == null || newStudent.getChuyenNganh().isEmpty()){
            throw new IllegalArgumentException("Các thuộc tính không được để trống");
        }
        if(newStudent.getTuoi() < 18){
            throw new IllegalArgumentException("Tuổi phải lớn hơn hoặc bằng 18");
        }
        if (newStudent.getAvgMark() < 0 || newStudent.getAvgMark() > 10){
            throw new IllegalArgumentException("Điểm phải nằm trong phạm vi từ 0 - 10");
        }
        student.setId(newStudent.getId());
        student.setName(newStudent.getName());
        student.setTuoi(newStudent.getTuoi());
        student.setAvgMark(newStudent.getAvgMark());
        student.setKiHoc(newStudent.getKiHoc());
    }
}
