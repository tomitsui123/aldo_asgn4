package winbuilder;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

/*	pending:
	1. load the sName and sID to the cbxStudent (students array is created), without duplication showing (only 1x Ivan Wong, 1 x Simon Li and 1 x Andy Paak)
	2. load only the cCode and cTitle to the listCourseList
	3. Enable the cbxGrade and btnSave if the grade is "NA"
	4. Update the updateGrade() and save the new grade to DB*/
	
public class Assignment04 extends JFrame{
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet rs = null;
	
	private JPanel contentPane;
	private JComboBox<Student> cbxStudent;

	
	ArrayList<Student> students = new ArrayList<Student>();
	private JList listCourseList;
	private JLabel lblCourseTitle;
	private JComboBox<String> cbxGrade;
	private JButton btnSave;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Assignment04 frame = new Assignment04();
					frame.setVisible(true);
					frame.addWindowListener(new WindowAdapter() {
			            @Override
			            public void windowClosing(WindowEvent e) {
			                System.out.println("WindowClosingDemo.windowClosing");
			        		closeDB();
			                System.exit(0);
			            }
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void initDB() {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String msAccDB = "Studentcourse.accdb";
		String dbURL = "jdbc:ucanaccess://" + msAccDB;
		
		try {
			connection = DriverManager.getConnection(dbURL);
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void retrieveDB() {
		String sqlStr = "SELECT * FROM GRADES";
		
		try {
			rs = statement.executeQuery(sqlStr);
			students = new ArrayList<Student>();
			while (rs.next()) {
				String name = rs.getString("sName");
				String sid = rs.getString("sId");
				String cCode = rs.getString("cCode");
				String cTitle = rs.getString("cTitle");
				Grade grade = Grade.valueOf(rs.getString("grade"));
				Student studentRecord = new Student(name, sid);
				if (!students.contains(studentRecord)) {
					studentRecord.addCourse(new Course(cCode, cTitle, grade));
					students.add(studentRecord);					
				} else {
					students.get(students.lastIndexOf(studentRecord)).addCourse(new Course(cCode, cTitle, grade));					
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


	}
	
	public static void closeDB() {
		try {
			connection.close();
			statement.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateGrade(String sid, String cCode, String grade) {
		// SQL Query TBC
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE GRADES SET GRADE='" + grade 
					+ "' WHERE SID='" + sid + "' AND CCODE='" + cCode + "'");
			ps.execute();
			retrieveDB();
			JOptionPane.showMessageDialog(null, "Grade Updated!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setupComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 504, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		lblCourseTitle = new JLabel("");
		lblCourseTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		cbxGrade = new JComboBox(Grade.values());
		cbxGrade.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
	            JComboBox comboBox = (JComboBox) e.getSource();
	            Grade selected = ((Grade) comboBox.getSelectedItem());
	            System.out.println(selected);
	            if (selected.equals(Grade.valueOf("NA"))) {
	            	btnSave.setEnabled(true);
	            	cbxGrade.setEnabled(true);
	            }
			}
		});
		cbxGrade.addActionListener(cbxGrade);
		cbxGrade.setEnabled(false);
		cbxGrade.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		cbxStudent = new JComboBox<Student>();
		cbxStudent.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cbxStudent.addItem(new Student("", ""));
		for(int i = 0; i < students.size(); i++) {
			Student currentStudent = students.get(i);
				cbxStudent.addItem(currentStudent);
//			}
		}
	    DefaultListModel model = new DefaultListModel();
		listCourseList = new JList(model);
		listCourseList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
                if (!e.getValueIsAdjusting()) {
    	            JList jList = (JList) e.getSource();
    	            Course selectedCourse = (Course) jList.getSelectedValue();
    	            if (selectedCourse != null) {
    	            	System.out.println(selectedCourse);    	            	
    	            	lblCourseTitle.setText(selectedCourse.getCourseTitle());
    	            	cbxGrade.setSelectedItem(selectedCourse.getCourseGrade());
    	            	System.out.println(selectedCourse.getCourseGrade());
    	            }                	
                }
			}
			
		});
		cbxStudent.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
	            JComboBox comboBox1 = (JComboBox) e.getSource();
	            Student selected = (Student) comboBox1.getSelectedItem();
	            System.out.println(selected);
	            model.clear();
	            for (int i = 0; i < selected.getCourses().length; i++) {
	            	model.addElement(selected.getCourses()[i]);
	            }
			}
		});
		
		// how to input the name and sId from the array without duplication
		
		btnSave = new JButton("Save");
		btnSave.setEnabled(false);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(cbxStudent.getSelectedItem());
				Student selectedStudent = (Student) cbxStudent.getSelectedItem();
				Course selectedCourse = (Course) listCourseList.getSelectedValue();
				Grade selectedGrade = (Grade) cbxGrade.getSelectedItem();
				updateGrade(selectedStudent.getSid(), selectedCourse.getCourseCode(), selectedGrade.toString());
				cbxGrade.setSelectedItem(selectedGrade);
	            if (!selectedGrade.equals(Grade.valueOf("NA"))) {
	            	btnSave.setEnabled(false);
	            	cbxGrade.setEnabled(false);
	            }
				
			}
			
		});

		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 20));


		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(18, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(listCourseList, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
						.addComponent(cbxStudent, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(cbxGrade, Alignment.LEADING, 0, 208, Short.MAX_VALUE)
								.addComponent(lblCourseTitle, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
							.addGap(20))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(79)
							.addComponent(btnSave)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(36)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbxStudent, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCourseTitle))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(cbxGrade, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
							.addGap(33)
							.addComponent(btnSave))
						.addComponent(listCourseList, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(52, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	/**
	 * Create the frame.
	 */
	public Assignment04() {
		initDB();
		retrieveDB();
		setupComponents();
	}
}


