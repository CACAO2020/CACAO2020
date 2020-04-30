package presentation.secondaire;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import abstraction.fourni.Journal;

/**
 * Classe modelisant une fenetre presentant graphiquement un Journal.
 * Cette classe est utilisee pour l'affichage des journaux.
 * 
 * Vous n'aurez pas, a priori, a utiliser directement cette classe
 * 
 * @author Romuald Debruyne
 */
public class FenetreJournal extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Journal journal;
	private JLabel label;
    private JCheckBox checkBox;
    
	public FenetreJournal(Journal j) {
		super(""+j.getNom());
		this.journal = j;

		this.setLayout(new BorderLayout());
		this.label = new JLabel();
		this.label.setText(this.journal.toHtml());
		this.label.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				journal.augmenterLignesAffichables();
				repaint();
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {} 
			
		});
		JScrollPane sp = new JScrollPane(label);
		this.add(sp, BorderLayout.CENTER);
		this.setSize(new Dimension(800,600));
		this.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent e) {
			FenetreJournal gg = (FenetreJournal)(e.getSource());
			gg.setVisible(false);
		
		   if (gg.checkBox!=null) {gg.checkBox.setSelected(false);} 
		   }});

	}
	
	@Override
	public void paint(Graphics g) {
		this.label.setText(journal.toHtml());
		super.paint(g);
	}
	
	public void setCheckBox(JCheckBox checkBox) {
		this.checkBox = checkBox;
	}
	public void setLabel(String s) {
		this.label.setText(s);
		if (this.isVisible()) {
			this.repaint();
		}
	}
}
