import javax.swing.*;
import view.FuncionárioView;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            System.out.println("Não foi possível definir o Look and Feel, usando o padrão.");
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FuncionárioView();
            }
        });
    }
}