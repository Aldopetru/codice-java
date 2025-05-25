package gui;

import controller.Controller;
import model.Check_list;
import model.ToDo;

import javax.swing.*;
import java.awt.*;

public class CheckListPanel extends JPanel {
    private DefaultListModel<Check_list> model;
    private JList<Check_list> lista;
    private JButton btnAggiungi, btnModifica, btnElimina, btnCompleta;

    private ToDo todo;

    public CheckListPanel(ToDo todo) {
        this.todo = todo;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 220)); // beige

        model = new DefaultListModel<>();
        lista = new JList<>(model);
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createTitledBorder("Checklist del ToDo"));

        todo.getChecklist().forEach(model::addElement);

        btnAggiungi = new JButton("➕ Aggiungi");
        btnModifica = new JButton("✏️ Modifica");
        btnElimina = new JButton("🗑️ Elimina");
        btnCompleta = new JButton("✔️ Completa");

        Color sabbia = new Color(222, 184, 135);
        for (JButton b : new JButton[]{btnAggiungi, btnModifica, btnElimina, btnCompleta}) {
            b.setBackground(sabbia);
            b.setFont(new Font("SansSerif", Font.BOLD, 12));
        }

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(245, 245, 220));
        btnPanel.add(btnAggiungi);
        btnPanel.add(btnModifica);
        btnPanel.add(btnCompleta);
        btnPanel.add(btnElimina);

        add(scroll, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        btnAggiungi.addActionListener(e -> {
            String voce = JOptionPane.showInputDialog(this, "Inserisci una nuova voce:");
            if (voce != null && !voce.isBlank()) {
                Controller.aggiungiCheck(todo, voce);
                model.addElement(new Check_list(voce, false));
            }
        });

        btnModifica.addActionListener(e -> {
            Check_list selezionata = lista.getSelectedValue();
            if (selezionata != null) {
                String nuova = JOptionPane.showInputDialog(this, "Modifica voce:", selezionata.getNome());
                if (nuova != null && !nuova.isBlank()) {
                    Controller.modificaCheck(selezionata, nuova);
                    lista.repaint();
                }
            }
        });

        btnCompleta.addActionListener(e -> {
            Check_list selezionata = lista.getSelectedValue();
            if (selezionata != null) {
                Controller.completaCheck(selezionata);
                lista.repaint();
            }
        });

        btnElimina.addActionListener(e -> {
            Check_list selezionata = lista.getSelectedValue();
            if (selezionata != null) {
                Controller.eliminaCheck(todo, selezionata);
                model.removeElement(selezionata);
            }
        });
    }
}

