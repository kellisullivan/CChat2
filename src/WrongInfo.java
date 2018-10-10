import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class WrongInfo {
	JFrame helpFrame; 
	
	public WrongInfo(String comment) {
		System.out.println("in second GUI");
		helpFrame = new JFrame();
		helpFrame.setSize(600,400);
		helpFrame.getContentPane().setBackground(Color.BLACK);
		helpFrame.getContentPane().setForeground(Color.YELLOW);
		helpFrame.getContentPane().setLayout(null);
		helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		createGUI(comment);
		helpFrame.setVisible(true);
	}
	
	public void createGUI(String comment) {
		
		JButton btnOkay = new JButton("OKAY");
		btnOkay.setForeground(new Color(0, 0, 0));
		btnOkay.setBackground(Color.YELLOW);
		btnOkay.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 26));
		btnOkay.setBounds(211, 219, 148, 69);
		btnOkay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				helpFrame.dispose();
				System.exit(0);
			}
		});
		System.out.println("all the way down here");
		helpFrame.getContentPane().add(btnOkay);
		
		JLabel lblNewLabel = new JLabel(comment);
		lblNewLabel.setBackground(Color.YELLOW);
		lblNewLabel.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setBounds(98, 94, 383, 69);
		helpFrame.getContentPane().add(lblNewLabel);
	}
}
