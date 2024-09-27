import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Laberinto extends javax.swing.JFrame implements ActionListener {
    private final ArrayList<Integer> camino = new ArrayList<>(); // Cambiado a Integer
    JButton botonInicio;
    int[][] mapa = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,1,1,0,1,1,1,1,0,1,1,0,1,0,1},
        {1,0,1,1,0,0,0,0,1,0,1,0,0,1,0,1},
        {1,0,1,1,1,1,1,0,1,0,0,0,1,1,0,1},
        {1,0,0,1,1,0,0,0,0,0,1,0,1,0,0,1},
        {1,1,0,1,1,0,1,0,1,1,1,0,1,0,1,1},
        {1,0,0,1,0,0,1,0,0,0,1,0,0,0,0,1},
        {1,0,1,1,0,1,1,1,1,0,1,1,1,1,0,1},
        {1,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1},
        {1,1,0,1,0,1,1,1,1,0,1,1,0,0,1,1},
        {1,1,0,1,0,0,0,1,1,0,0,1,1,0,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,9,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    public Laberinto() {
        this.setBounds(50, 50, 500, 600);
        this.setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        botonInicio = new JButton("Iniciar");
        botonInicio.setBounds(200, 450, 100, 30);
        botonInicio.addActionListener(this);
        this.add(botonInicio);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int row = 0; row < mapa.length; row++) {
            for (int col = 0; col < mapa[0].length; col++) {
                Color color;
                switch (mapa[row][col]) {
                    case 1:
                        color = Color.BLACK; // Pared
                        break;
                    case 9:
                        color = Color.RED; // Salida
                        break;
                    default:
                        color = Color.WHITE; // Espacio vacío
                }
                g2d.setColor(color);
                g2d.fillRect(30 * col, 30 * row, 30, 30);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(30 * col, 30 * row, 30, 30);
            }
        }

        // Dibujar el camino encontrado en verde
        g2d.setColor(Color.GREEN);
        for (Integer index : camino) {
            int x = index / mapa[0].length; // Obtener fila
            int y = index % mapa[0].length; // Obtener columna
            g2d.fillRect(30 * y, 30 * x, 30, 30);       
		}
    }

    public boolean BusquedaProfunda(int x, int y) {
        // Verificar límites
        if (x < 0 || x >= mapa.length || y < 0 || y >= mapa[0].length) {
            return false;
        }

        // Verificar si es la salida
        if (mapa[x][y] == 9) {
            camino.add(x * mapa[0].length + y); // Agregar la salida al camino
            return true;
        }

        // Verificar si es un espacio vacío
        if (mapa[x][y] == 0) {
            mapa[x][y] = 2; // Marcamos como visitado
            camino.add(x * mapa[0].length + y); // Agregar el índice al camino

            // Recursión en las cuatro direcciones
            if (BusquedaProfunda(x + 1, y) || // Abajo
                BusquedaProfunda(x - 1, y) || // Arriba
                BusquedaProfunda(x, y + 1) || // Derecha
                BusquedaProfunda(x, y - 1)) { // Izquierda
                return true;
            }

            // Si no se encontró el camino, desmarcamos
            camino.remove(camino.size() - 1);
            mapa[x][y] = 0; // Desmarcar
        }

        return false;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonInicio) {
            camino.clear(); // Limpiar camino previo
            BusquedaProfunda(2, 1); // Iniciar desde la entrada (2,1)
            repaint(); // Volver a dibujar
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Laberinto().setVisible(true));
    }
}
