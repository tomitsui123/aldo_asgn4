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
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*	pending:
	1. load the sName and sID to the cbxStudent (students array is created), without duplication showing (only 1x Ivan Wong, 1 x Simon Li and 1 x Andy Paak)
	2. load only the cCode and cTitle to the listCourseList
	3. Enable the cbxGrade and btnSave if the grade is "NA"
	4. Update the updateGrade() and save the new grade to DB*/
	
public class Assignment04 extends JFrame {
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet rs = null;
	
	private JPanel contentPane;
	private JComboBox cbxStudent;

	
	ArrayList<Student> students = new ArrayList<>();
	private JList listCourseList;
	private JLabel lblCourseTitle;
	private JComboBox cbxGrade;
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
//		String msAccDB = "Question.accdb";
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
			while (rs.next()) {
//				int id = rs.getInt("ID");
				String name = rs.getString("sName");
				String sid = rs.getString("sId");
				String cCode = rs.getString("cCode");
				String cTitle = rs.getString("cTitle");
				String grade = rs.getString("grade");
//				System.out.println(name + sid);
				
				Student studentRecord = new Student(name, sid, cCode, cTitle, grade);
				students.add(studentRecord);
				
//				cbxStudent.add
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


	}
	
	public void closeDB() {
		try {
			connection.close();
			statement.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createEvents() {
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateGrade();
			}
		});
	}
	
	public void updateGrade() {
		// SQL Query TBC
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE GRADES SET GRADE='P' WHERE SID='300312345' AND CCODE='CSIS3275'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setupComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 504, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		lblCourseTitle = new JLabel("New label");
		lblCourseTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		cbxStudent = new JComboBox();
		cbxStudent.setFont(new Font("Tahoma", Font.PLAIN, 16));

		// how to input the name and sId from the array without duplication
		
		
		cbxGrade = new JComboBox();
		cbxGrade.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		btnSave = new JButton("Save");

		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		listCourseList = new JList();
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
		closeDB();
		setupComponents();
		createEvents();
	}
}


