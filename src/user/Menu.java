package user;

// 菜单主页

import SC.AddSC;
import SC.DeleteSC;
import SC.QuerySC;
import SC.UpdateSC;
import course.AddCourse;
import course.DeleteCourse;
import course.QueryCourse;
import course.UpdateCourse;
import student.AddStudent;
import student.DeleteStudent;
import student.UpdateStudent;
import student.QueryStudent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener {

    JLabel label = null;    // 创建标签
    CardLayout card = null; // 创建卡片式布局
    JPanel pCenter = null;  // 创建面板

    // 设置菜单栏选项
    JMenuBar menubar = new JMenuBar();

    JMenu stumng = new JMenu("学生管理");
    JMenuItem addstu = new JMenuItem("增加学生   ");
    JMenuItem updstu = new JMenuItem("修改学生   ");
    JMenuItem delstu = new JMenuItem("删除学生   ");

    JMenu coumng = new JMenu("课程管理");
    JMenuItem addcou = new JMenuItem("增加课程   ");
    JMenuItem updcou = new JMenuItem("修改课程   ");
    JMenuItem delcou = new JMenuItem("删除课程   ");

    JMenu scmng = new JMenu("选课管理");
    JMenuItem addsc = new JMenuItem("增加选课   ");
    JMenuItem updsc = new JMenuItem("修改选课   ");
    JMenuItem delsc = new JMenuItem("删除选课   ");

    JMenu query = new JMenu("查询管理");
    JMenuItem questu = new JMenuItem("学生信息查询   ");
    JMenuItem quecou = new JMenuItem("课程信息查询   ");
    JMenuItem quesc = new JMenuItem("选课信息查询   ");


    public Menu() {
        this.setTitle("学生选课管理系统");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Exception:" + e);
        }

        // 添加菜单
        addMenu1();
        addMenu2();
        addMenu3();
        addMenu4();
        addJMenuBar();
        setJMenuBar(menubar);

        // 设置点击事件
        addstu.addActionListener(this);
        updstu.addActionListener(this);
        delstu.addActionListener(this);
        addcou.addActionListener(this);
        delcou.addActionListener(this);
        updcou.addActionListener(this);
        addsc.addActionListener(this);
        delsc.addActionListener(this);
        updsc.addActionListener(this);
        questu.addActionListener(this);
        quecou.addActionListener(this);
        quesc.addActionListener(this);
        quecou.addActionListener(this);
        quesc.addActionListener(this);
        // 创建标签
        label = new JLabel("选课管理系统", JLabel.CENTER);
        label.setFont(new Font("宋体", Font.BOLD, 36));
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setForeground(Color.BLUE);

        card = new CardLayout();     // 创建卡片式布局
        pCenter = new JPanel();      // 创建面板
        pCenter.setLayout(card);


        AddStudent addStudent = new AddStudent();
        DeleteStudent deleteStudent = new DeleteStudent();
        UpdateStudent updateStudent = new UpdateStudent();

        AddCourse addCourse = new AddCourse();
        DeleteCourse deleteCourse = new DeleteCourse();
        UpdateCourse updateCourse = new UpdateCourse();

        AddSC addSC = new AddSC();
        DeleteSC deleteSC = new DeleteSC();
        UpdateSC updateSC = new UpdateSC();
        //SelectStudent selectStudent = new SelectStudent();
        QueryStudent queryStudent = new QueryStudent();
        QueryCourse queryCourse = new QueryCourse();
        QuerySC querySC = new QuerySC();
        // 设置主界面和子界面
        pCenter.add("欢迎界面", label);
        pCenter.add("增加学生界面", addStudent);
        pCenter.add("修改学生界面", updateStudent);
        pCenter.add("删除学生界面", deleteStudent);
        pCenter.add("增加课程界面", addCourse);
        pCenter.add("删除课程界面", deleteCourse);
        pCenter.add("修改课程界面", updateCourse);
        pCenter.add("增加选课界面", addSC);
        pCenter.add("删除选课界面", deleteSC);
        pCenter.add("修改选课界面", updateSC);
        pCenter.add("学生查询界面", queryStudent);
        pCenter.add("课程查询界面", queryCourse);
        pCenter.add("选课查询界面", querySC);

        add(pCenter, BorderLayout.CENTER);
        validate();
        setVisible(true);
        setResizable(false);
        setBounds(400, 150, 600, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        validate();
    }

    // 添加每个菜单栏的子菜单
    private void addMenu1() {
        stumng.add(addstu);
        stumng.add(updstu);
        stumng.add(delstu);
    }

    private void addMenu2() {
        coumng.add(addcou);
        coumng.add(updcou);
        coumng.add(delcou);
    }

    private void addMenu3() {
        scmng.add(addsc);
        scmng.add(updsc);
        scmng.add(delsc);
    }

    private void addMenu4() {
        query.add(questu);
        query.add(quecou);
        query.add(quesc);
    }

    // 将每个子菜单添加到菜单栏
    private void addJMenuBar() {
        menubar.add(stumng);
        menubar.add(coumng);
        menubar.add(scmng);
        menubar.add(query);
    }

    // 菜单事件响应
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == addstu) {
            card.show(pCenter, "增加学生界面");
        } else {
            if (obj == updstu) {
                card.show(pCenter, "修改学生界面");
            } else {
                if (obj == delstu) {
                    card.show(pCenter, "删除学生界面");
                } else {
                    if (obj == addcou) {
                        card.show(pCenter, "增加课程界面");
                    } else {
                        if (obj == delcou) {
                            card.show(pCenter, "删除课程界面");
                        } else {
                            if (obj == updcou) {
                                card.show(pCenter, "修改课程界面");
                            } else {
                                if (obj == addsc) {
                                    card.show(pCenter, "增加选课界面");
                                } else {
                                    if (obj == delsc) {
                                        card.show(pCenter, "删除选课界面");
                                    } else {
                                        if (obj == updsc) {
                                            card.show(pCenter, "修改选课界面");
                                        } else {
                                            if (obj == questu) {
                                                card.show(pCenter, "学生查询界面");
                                            }else {
                                                if(obj == quecou){
                                                    card.show(pCenter,"课程查询界面");
                                                }else{
                                                    if(obj == quesc){
                                                        card.show(pCenter,"选课查询界面");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
