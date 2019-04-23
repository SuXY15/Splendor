package app;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.DefaultListModel;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JList;
public final class LayerTester extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8940677778932874928L;
	public LayerTester() {
        super("JLayeredPane");
        JList jl1 = new JList();
        DefaultListModel dlm = new DefaultListModel();
        dlm.addElement("asdfwfewfw");
        dlm.addElement("asdfwf124234ewfw");
        dlm.addElement("asdfwf123ewfw");
        jl1.setModel(dlm);
        JEditorPane jep1 = new JEditorPane();
        jep1.setBackground(Color.yellow);
        jep1.setBounds(0, 0, 300, 300); //在这里调整控件的显示位置和大小
        jl1.setBounds(100,100,100,400); //在这里调整控件的显示位置和大小
         
        JLayeredPane layeredPane = getLayeredPane();
        layeredPane.add(jep1, 10, 1);
        layeredPane.add(jl1, 10, 0);
        setSize(new Dimension(280, 280));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
    }
    public static void main(String[] args) {
        new LayerTester();
    }
}