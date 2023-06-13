public class systemProgramming {
    public static void main(String[] args) {
        int count = scanner.nextInt();
        for (int i=0; i< count; i++) {
            Student student = new Student();
            sum = student.sum();
            System.out.println("sum: "+sum);
            average = student.average();
            System.out.println("average: "+average);
        }
    }
}

class Student{
    private int kor = 60;
    private int eng = 70;

    public Student(int kor, int eng) {
        this.kor = kor;
        this.eng = eng;
    }

    public int sum() {
        int sum = this.kor+this.eng;
        return sum;
    }

    public int avg() {
        int average = (this.kor+this.eng)/2;
        return average;
    }
}