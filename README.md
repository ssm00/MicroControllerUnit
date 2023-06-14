# MicroControllerUnit

![image](https://github.com/ssm00/MicroControllerUnit/assets/97657265/3d21d069-c0a8-4054-ac6a-3c2709ba42a9)


public class systemProgramming {
    public static void main(String[] args) {
        int count = scanner.nextInt();
        int sum;
        int average;
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


--------------------------------------------------------------------


Symbol table
Int count	Data.0
Int sum	Data.4
Int average	Data.8
Int i	Data.12
Student student	Data.16
Student student	Data.20
Student student	Data.24

heap
Int kor	Hs.0
Int eng	Hs.4
Int kor	Hs.8
Int eng	Hs.12
Int kor	Hs.16
Int eng	Hs.20
Sum stack
Dynamic link	Sp.0
Int return 	Sp.4
int sum	Sp.8
Retrun address	Sp.12
average stack
Dynamic link	Sp.0
Int return 	Sp.4
int average	Sp.8
Retrun address	Sp.12






----------------------------assembly---------------------




0	input
1	set ds
2	stoa count
3	loadc 0
4	stoa i
	start:
5	loada i
6	suba count
7	gtj end
8	loada i
9	addc 1
10	stoa i
11	set ds
12	loadr hp
13	addr hs
14	stoa student
15	set hs
16	loadc 60
17	stoa hp
18	pushh 4
19	loadc 70
20	stoa hp
21	pushh 4
22	set ss
23	loadr sp
24	stoa sp
25	pushs 4
26	stoa sp
27	pushs 4
28	stoa sp
29	pushs 4
30	loadc 33
31	stoa sp
32	jump fsum
33	set ss
34	loada 4
35	set ds
36	stoa sum
37	Pop
38	print sum
39	set ss
40	loadr sp
41	stoa sp
42	pushs 4
43	stoa sp
44	pushs 4
45	stoa sp
46	pushs 4
47	loadc 50
48	stoa sp
49	jump favg
50	set ss
51	loada 4
52	set ds
53	stoa average
54	pop
55	print average
56	jump start
	end:
57	halt
	fsum:
58	set hs
59	loada 0
60	adda 4
61	set ss
62	stoa 8
63	loada 8
64	stoa 4
65	loada 12
66	jumpra
	favg:
67	set hs
68	loada 0
69	adda 4
70	div 2
71	set ss
72	stoa 8
73	loada 8
74	stoa 4
75	loada 12
76	jumpra


