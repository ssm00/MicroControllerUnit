public class systemProgramming {
    public static void main(String[] args) {
        int count = scanner.nextInt();
        int sum;
        int average;
        for (int i=0; i< count; i++) {
            Student student = new Student();
            sum = student.sum();
            System.out.println(sum);
            average = student.average();
            System.out.println(average);
        }
    }
}

class Student{
    private int kor = 60;
    private int eng = 70;

    public int sum() {
        int sum = this.kor+this.eng;
        return sum;
    }

    public int avg() {
        int average = (this.kor+this.eng)/2;
        return average;
    }
}