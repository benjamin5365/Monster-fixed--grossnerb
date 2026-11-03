package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * ActionButtonPanel - Four customizable action buttons with a Lapis theme.
 * 
 * Features a subtle vertical hover-lift elevation effect with accent glow borders 
 * and drop shadows.
 */
public class ActionButtonPanel extends JPanel {
    
    private JButton[] buttons;
    private ActionListener actionListener;
    
    // Default button labels
    private static final String[] DEFAULT_LABELS = {
        "Action 1", "Action 2", "Action 3", "Action 4"
    };
    
    // Lapis Visual Constants
    private static final Color PANEL_BG = new Color(11, 23, 44);             // Deep Lapis Night
    private static final Color BUTTON_BG = new Color(24, 43, 73);             // Deep Lapis Blue
    private static final Color BUTTON_HOVER = new Color(34, 64, 108);         // Radiant Lapis Cobalt
    private static final Color BUTTON_PRESSED = new Color(15, 30, 55);        // Dark Midnight Blue
    private static final Color BUTTON_DISABLED = new Color(25, 33, 44);       // Muted Dark Slate
    private static final Color TEXT_COLOR = new Color(240, 246, 252);         // High-Contrast Ice White
    private static final Color TEXT_DISABLED = new Color(100, 116, 139);      // Muted Slate Text
    
    // Accent Colors
    private static final Color BORDER_NORMAL = new Color(48, 86, 140);       // Cobalt Border
    private static final Color BORDER_HOVER = new Color(255, 215, 64);       // Radiant Gold Glow Border
    private static final Color SHADOW_COLOR = new Color(5, 10, 20, 160);      // Soft Drop Shadow Accent
    
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 15);
    
    /**
     * Constructor
     * @param listener ActionListener to handle button clicks
     */
    public ActionButtonPanel(ActionListener listener) {
        this.actionListener = listener;
        this.buttons = new JButton[4];
        
        setLayout(new GridLayout(1, 4, 12, 0));
        setBackground(PANEL_BG);
        
        createButtons();
    }
    
    /**
     * Create the four action buttons
     */
    private void createButtons() {
        for (int i = 0; i < 4; i++) {
            buttons[i] = createStyledButton(DEFAULT_LABELS[i], i);
            add(buttons[i]);
        }
    }
    
    /**
     * Create a styled button with hover-lift elevation
     * @param label Button label
     * @param index Button index (0-3)
     * @return The styled button
     */
    private JButton createStyledButton(String label, int index) {
        LapisHoverButton button = new LapisHoverButton(Emoji.fit(label, BUTTON_FONT));
        button.setFont(BUTTON_FONT);
        button.setForeground(TEXT_COLOR);
        button.setActionCommand(String.valueOf(index));
        button.addActionListener(actionListener);
        return button;
    }
    
    /**
     * Set custom labels for the buttons
     * @param labels Array of 4 labels
     */
    public void setButtonLabels(String[] labels) {
        if (labels.length != 4) {
            throw new IllegalArgumentException("Must provide exactly 4 labels");
        }
        
        for (int i = 0; i < 4; i++) {
            buttons[i].setText(Emoji.fit(labels[i], BUTTON_FONT));
        }
    }
    
    /**
     * Enable or disable all buttons
     * @param enabled true to enable, false to disable
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (JButton button : buttons) {
            button.setEnabled(enabled);
        }
    }
    
    /**
     * Enable or disable a specific button
     * @param index Button index (0-3)
     * @param enabled true to enable, false to disable
     */
    public void setButtonEnabled(int index, boolean enabled) {
        if (index >= 0 && index < 4) {
            buttons[index].setEnabled(enabled);
        }
    }

    /**
     * Custom JButton with vertical hover-lift elevation effect.
     */
    private static class LapisHoverButton extends JButton {
        private boolean hovering = false;
        private boolean pressed = false;

        public LapisHoverButton(String text) {
            super(text);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (isEnabled()) {
                        hovering = true;
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                        repaint();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovering = false;
                    pressed = false;
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    repaint();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (isEnabled()) {
                        pressed = true;
                        repaint();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (isEnabled()) {
                        pressed = false;
                        repaint();
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            // Calculate vertical lift offset (-4px on hover, +1px on click)
            int liftOffset = 0;
            if (isEnabled()) {
                if (pressed) {
                    liftOffset = 1;
                } else if (hovering) {
                    liftOffset = -4;
                }
            }

            // Draw elevation drop shadow when hovered
            if (hovering && isEnabled() && liftOffset < 0) {
                g2.setColor(SHADOW_COLOR);
                g2.fillRect(2, height - 4, width - 4, 4);
            }

            // Background color state
            Color bgColor;
            if (!isEnabled()) {
                bgColor = BUTTON_DISABLED;
            } else if (pressed) {
                bgColor = BUTTON_PRESSED;
            } else if (hovering) {
                bgColor = BUTTON_HOVER;
            } else {
                bgColor = BUTTON_BG;
            }

            // Apply lift translation for button body & border
            g2.translate(0, liftOffset);

            // Draw button background body
            g2.setColor(bgColor);
            g2.fillRect(0, 0, width, height - (hovering && isEnabled() ? 4 : 0));

            // Draw border
            Color borderColor = !isEnabled() ? BUTTON_DISABLED : (hovering ? BORDER_HOVER : BORDER_NORMAL);
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(hovering && isEnabled() ? 2.5f : 2.0f));
            g2.drawRect(1, 1, width - 2, height - (hovering && isEnabled() ? 5 : 2));

            g2.dispose();

            // Set text color state
            if (!isEnabled()) {
                setForeground(TEXT_DISABLED);
            } else {
                setForeground(TEXT_COLOR);
            }

            // Shift text rendering along with lift offset
            Graphics textGraphics = g.create();
            textGraphics.translate(0, liftOffset);
            super.paintComponent(textGraphics);
            textGraphics.dispose();
        }
    }
}