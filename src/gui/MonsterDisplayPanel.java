package gui;
import game.Monster;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * MonsterDisplayPanel - Displays monster tiles in a responsive grid
 * 
 * Styled with a Lapis Lazuli theme (deep ultramarine blue, cobalt borders,
 * and high-contrast gold accents).
 */
public class MonsterDisplayPanel extends JPanel {
    
    private ArrayList<Monster> monsters;
    private ArrayList<MonsterTile> tiles;
    private int highlightedIndex = -1;
    
    // Lapis Theme Color Palette
    private static final Color BACKGROUND_COLOR = new Color(11, 23, 44);      // Deep Lapis Night
    private static final Color PANEL_BORDER_COLOR = new Color(56, 189, 248);  // Celestial Cyan Accent
    private static final Color TITLE_COLOR = new Color(224, 242, 254);         // Ice Blue Text
    private static final int TILE_SPACING = 12;
    
    /**
     * Constructor
     */
    public MonsterDisplayPanel() {
        monsters = new ArrayList<>();
        tiles = new ArrayList<>();
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PANEL_BORDER_COLOR, 2),
            "MONSTERS",
            0, 0, new Font("Segoe UI", Font.BOLD, 16), TITLE_COLOR));
    }
    
    /**
     * Set the monsters to display
     * @param monsters The list of monsters
     */
    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
        this.highlightedIndex = -1;
        createTiles();
    }
    
    /**
     * Highlight a specific monster
     * @param index The index to highlight (-1 for none)
     */
    public void highlightMonster(int index) {
        this.highlightedIndex = index;
        repaint();
    }
    
    /**
     * Create tiles for all monsters
     */
    private void createTiles() {
        removeAll();
        tiles.clear();
        
        if (monsters == null || monsters.isEmpty()) {
            JLabel emptyLabel = new JLabel("No monsters to display");
            emptyLabel.setForeground(TITLE_COLOR);
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            add(emptyLabel);
            revalidate();
            repaint();
            return;
        }
        
        // Determine grid layout
        int monsterCount = monsters.size();
        int cols = (int) Math.ceil(Math.sqrt(monsterCount));
        int rows = (int) Math.ceil((double) monsterCount / cols);
        
        setLayout(new GridLayout(rows, cols, TILE_SPACING, TILE_SPACING));
        
        // Create a tile for each monster
        for (int i = 0; i < monsters.size(); i++) {
            MonsterTile tile = new MonsterTile(monsters.get(i), i);
            tiles.add(tile);
            add(tile);
        }
        
        revalidate();
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Update highlighting
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).setHighlighted(i == highlightedIndex);
        }
    }
    
    /**
     * MonsterTile - Individual monster display card
     */
    private static class MonsterTile extends JPanel {
        private Monster monster;
        private int index;
        private boolean highlighted = false;
        
        // Lapis Theme Tile Colors
        private static final Color TILE_BG = new Color(24, 43, 73);              // Rich Lapis Blue
        private static final Color TILE_BORDER = new Color(48, 86, 140);          // Cobalt Accent
        private static final Color HIGHLIGHT_COLOR = new Color(255, 215, 64);     // Gold Fleck Accent
        private static final Color DEAD_COLOR = new Color(20, 28, 42);            // Fade/Defeated Slate
        
        private static final Color HEALTH_BAR_BG = new Color(11, 23, 44);         // Deep Dark Base
        private static final Color HEALTH_BAR_FULL = new Color(0, 191, 255);      // Lapis Blue Glow
        private static final Color HEALTH_BAR_MED = new Color(255, 183, 3);       // Amber Gold
        private static final Color HEALTH_BAR_LOW = new Color(239, 68, 68);       // Crimson Low Health
        
        public MonsterTile(Monster monster, int index) {
            this.monster = monster;
            this.index = index;
            setLayout(new BorderLayout(5, 5));
            setBackground(monster.health() > 0 ? TILE_BG : DEAD_COLOR);
            setBorder(BorderFactory.createLineBorder(TILE_BORDER, 2));
            createLayout();
        }
        
        private void createLayout() {
            // Monster info panel
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setOpaque(false);
            
            // Monster number/name
            JLabel numberLabel = new JLabel("Monster #" + index);
            numberLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            numberLabel.setForeground(new Color(240, 246, 252));
            numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            // Stats
            JLabel healthLabel = new JLabel("HP: " + monster.health());
            healthLabel.setForeground(new Color(186, 230, 253));
            healthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            healthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel damageLabel = new JLabel("DMG: " + monster.damage());
            damageLabel.setForeground(new Color(255, 215, 64)); // Gold accent
            damageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            damageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel speedLabel = new JLabel("SPD: " + monster.speed());
            speedLabel.setForeground(new Color(56, 189, 248)); // Bright Lapis Cyan
            speedLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            speedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            // Special ability if exists
            String special = monster.special();
            if (special != null && !special.isEmpty()) {
                Font specialFont = new Font("Segoe UI", Font.ITALIC, 11);
                JLabel specialLabel = new JLabel(Emoji.fit("★ " + special, specialFont));
                specialLabel.setForeground(new Color(255, 223, 128));
                specialLabel.setFont(specialFont);
                specialLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                infoPanel.add(specialLabel);
            }
            
            infoPanel.add(numberLabel);
            infoPanel.add(Box.createVerticalStrut(5));
            infoPanel.add(healthLabel);
            infoPanel.add(damageLabel);
            infoPanel.add(speedLabel);
            
            add(infoPanel, BorderLayout.CENTER);
            
            // Health bar at bottom
            add(new HealthBar(), BorderLayout.SOUTH);
        }
        
        public void setHighlighted(boolean highlighted) {
            if (this.highlighted != highlighted) {
                this.highlighted = highlighted;
                setBorder(BorderFactory.createLineBorder(
                    highlighted ? HIGHLIGHT_COLOR : TILE_BORDER, 
                    highlighted ? 3 : 2));
                repaint();
            }
        }
        
        /**
         * Visual health bar
         */
        private class HealthBar extends JPanel {
            private static final int BAR_HEIGHT = 16;
            
            public HealthBar() {
                setPreferredSize(new Dimension(0, BAR_HEIGHT));
                setBackground(HEALTH_BAR_BG);
            }
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                int width = getWidth();
                int height = getHeight();
                
                // Calculate health percentage
                double healthPercent = Math.max(0, Math.min(100, monster.health())) / 100.0;
                int barWidth = (int) (width * healthPercent);
                
                // Choose color based on health
                Color barColor;
                if (healthPercent > 0.5) {
                    barColor = HEALTH_BAR_FULL;
                } else if (healthPercent > 0.25) {
                    barColor = HEALTH_BAR_MED;
                } else {
                    barColor = HEALTH_BAR_LOW;
                }
                
                // Draw health bar fill
                g.setColor(barColor);
                g.fillRect(0, 0, barWidth, height);
                
                // Draw thin inner lapis border
                g.setColor(new Color(48, 86, 140));
                g.drawRect(0, 0, width - 1, height - 1);
            }
        }
    }
}