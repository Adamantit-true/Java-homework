package semester1.practice02.homework;

public class Task5RpgHero {
    public static void main(String[] args) {

        String playerName = "Illidan";
        String playerClass = "Demon Hunter";

        int strength = (int) (Math.random() * (18 - 8 + 1)) + 8;
        int agility = (int) (Math.random() * (18 - 8 + 1)) +  8;
        int intelligence = (int) (Math.random() * (18 - 8 + 1)) + 8;

        int hp = strength * 10 + agility * 2;
        int mp = intelligence * 15;
        int armor = agility / 2;

        int dice1 = (int) (Math.random() * 6) + 1;
        int dice2 = (int) (Math.random() * 6) + 1;

        int playerAttack = dice1 + dice2 + strength;
        final int MONSTER_ATTACK = 22;

        boolean isPlayerVictory = playerAttack > MONSTER_ATTACK;

        System.out.println();
        System.out.println("/**************************************************\\");
        System.out.println("|                 ПАСПОРТ ГЕРОЯ RPG                |");
        System.out.println("\\**************************************************/");
        System.out.println("Ім'я персонажа:         "+playerName);
        System.out.println("Клас:                   "+playerClass);
        System.out.println();
        System.out.println("[ХАРАКТЕРИСТИКИ]");
        System.out.println("Сила (STR):             "+strength);
        System.out.println("Спритність (AGI):       "+agility);
        System.out.println("Інтелект (INT):         "+intelligence);
        System.out.println();
        System.out.println("[ПОХІДНІ АТРИБУТИ]");
        System.out.println("Очки здоров'я (HP):     "+hp);
        System.out.println("Очки мани (MP):         "+mp);
        System.out.println("Клас броні (Armor):     "+armor);
        System.out.println();
        System.out.println("[СИМУЛЯЦІЯ БОЮ]");
        System.out.println("Кидок кубиків гравця:   "+dice1+ "+" +dice2+ " (бонус сили: +" + strength + ")");

        System.out.println("Загальна атака гравця:  "+playerAttack);
        System.out.println("Сила удару монстра:     "+MONSTER_ATTACK);
        System.out.println("Чи переміг гравець?     "+isPlayerVictory);
        System.out.println("\\**************************************************/");


    }
}
