package presentation;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.junit.Test;

public class FenetrePrincipaleTest {

	@Test
	public void test() {
		FenetrePrincipale fp = new FenetrePrincipale();
		for (int i=0; i<100; i++)
			((JButton) ((JPanel) fp.getRootPane().getContentPane().getComponent(2)).getComponent(0)).doClick();
		
		for (int i=0; i<10; i++)
			((JButton) ((JPanel) fp.getRootPane().getContentPane().getComponent(2)).getComponent(1)).doClick();
		
		((JButton) ((JPanel) fp.getRootPane().getContentPane().getComponent(2)).getComponent(2)).doClick();
	}

}
