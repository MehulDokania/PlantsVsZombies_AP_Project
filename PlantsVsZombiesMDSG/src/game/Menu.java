package game;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JPanel {

    Image bgImage;
    JLabel name;
    
    public Menu() {
        initComponents();
        setSize(1012, 785);
        try{
            bgImage  = new ImageIcon(this.getClass().getResource("/res/menu.jpg")).getImage();
        }
        catch(Exception e){
            System.out.println("Could not load image");
            e.printStackTrace();
        }
    
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() {

        JPanel jPanel1 = new JPanel();
        name = new JLabel();

        setPreferredSize(new Dimension(1012, 785));

        jPanel1.setOpaque(false);
        jPanel1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 387, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 116, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(523, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(547, Short.MAX_VALUE))
        );
        name.setBounds(50,50, 100,30);  
        name.setText("label text");
        name.setFont(new Font("Serif", Font.BOLD, 50));
        name.setForeground(Color.YELLOW);
        jPanel1.add(name);
    }

	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(bgImage,0,0,null);
	    
	}
	
}