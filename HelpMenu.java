import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class HelpMenu extends JPanel{

	private JMenuItem helpMenu = new JMenuItem("idkkkk");
	private JPopupMenu menuPopUp = new JPopupMenu();

	public HelpMenu(){
		this.add(new JLabel("Label idk"));
		menuPopUp.add(helpMenu);
		this.setComponentPopupMenu(menuPopUp);
	}

	private void display(){
		JFrame jframe = new JFrame("Help Menu");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(this);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);
	}

	public static void main(String [] args){
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run(){
				new HelpMenu().display();
			}
		});
	}

}
