package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.builders.JsonBuilder;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.vaadin.client.JsArrayObject;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData;

public interface HData extends JsonBuilder {
    
    List<Hero> getHeroes();
    HData setHeroes(List<Hero> l);
    
    public interface Hero extends JsonBuilder {
        Hero setRank(int i);
        int getRank();
        Hero setName(String s);
        String getName();
        Hero setHeroname(String s);
        String getHeroname();
        Hero setVitals(HVitals v);
        HVitals getVitals();
        Hero setLocation(HLocation l);
        HLocation getLocation();
        Hero setStatus(String s);
        String getStatus();
        Hero setIq(int i);
        int getIq();
        Hero setHeight(int i);
        int getHeight();
        Hero setWeight(int i);
        int getWeight();
        Hero setKillcount(int i);
        int getKillcount();
        Hero setArchenemy(String s);
        String getArchenemy();
        Hero setMission(String s);
        String getMission();
    }
    
    public interface HVitals extends JsonBuilder {
        List<Integer> getHeart();
        HVitals setHeart(List<Integer> l);
        List<Integer> getCoffee();
        HVitals setCoffee(List<Integer> l);
    }

    public interface HLocation extends JsonBuilder {
        double longitude();
        HLocation longitude(double d);
        double latitude();
        HLocation latitude(double d);
    }
    
    public static class MockData {
        private static String[] actions = { "Fix", "Implement", "Disable",
                "Activate", "Design", "Export", "Import", "Produce", "Invent",
                "Establish", "Feed", "Launch", "Deliver", "Polish" };
        private static String[] targets = { "Grid", "world peace",
                "teleportation", "Vaadin", "the dog", "the weather", "soup",
                "results" };
        private static String[] forenames = { "Teemu", "Johannes", "Patrik",
                "John", "Henrik", "Leif", "Artur", "Joonas" };
        private static String[] surnames = { "Suo-Anttila", "Dahlström",
                "Lindström", "Ahlroos", "Paul", "Åstrand", "Signell",
                "Lehtinen" };
        
        private static String[] heroes = {
            "The Dapper Mantis",
            "The Gray Charmer",
            "The Misty Prince",
            "The Fearless Mothman",
            "Good Lion",
            "Gentle Hawk",
            "Gentle Axeman",
            "Captain Galactic Puma",
            "Wildfire",
            "Ethereal Titan",
            "The Defiant Sentinel",
            "The Nuclear Leopard",
            "The Godly Mercenary",
            "The Ice Scorpion",
            "Calm Catman",
            "Brave Genius",
            "Quiet Oxman",
            "Good Catman",
            "Rosethorn",
            "Red Heart",
            "The Fearless Swordsman",
            "The Quantum Shield",
            "The Famous Bear",
            "The Infamous Detective",
            "Chief Red Lynx",
            "Master Gentle Mothman",
            "Warden Electric Wonder",
            "Giant Falcon",
            "Heloth",
            "Scarlet HorseShare",
            "The Spectacular Sage",
            "The Golden Nighthawk",
            "The Ethereal Shadow",
            "The Yellow Guard",
            "Lord Wacky Charmer",
            "Professor Intelligent Dagger",
            "Captain Righteous Shade",
            "Professor Swift Gorilla",
            "Master Defiance",
            "Share this generatorfacebook share  tweet  google plus",
            "The Spectacular Sage",
            "The Golden Nighthawk",
            "The Ethereal Shadow",
            "The Yellow Guard",
            "Lord Wacky Charmer",
            "Professor Intelligent Dagger",
            "Captain Righteous Shade",
            "Professor Swift Gorilla",
            "Master Defiance"
        };
        
        static String[] statuses = {
            "Vacation",
            "On a Mission",
            "Shopping",
            "Hanging out",
            "On a Mission",
            "Sleeping",
            "Exhausted",
            "On a Quest",
            "Trainning",
            "Terminated",
        };
        
        static String[] fantasies = {
            "An elf named Mirasaer seeks a party to find and explore the ancient ruins of the Bastion of Vano the Deranged.",
            "An angry priest named Lansiusim seeks a party to explore the Pisiba Jungle.",
            "An elf named Ferda seeks a party to recover and destroy an evil artifact from the Forsaken Gauntlet of Horror.",
            "A cryptic elven lady named Niserie seeks a party to hunt down and capture the remorseless killer Thelcha.",
            "An elven lady named Nerduilye seeks a party to thwart the monstrous plan of the Wraith of the Silent Jungle. Moreover, the party must complete the quest without killing anyone.",
            "An angry noblewoman named Bausse seeks a party to escort a valued family heirloom safely to the dwarven city of Thorifell. Moreover, the party encounters an old ally now working against them.",
            "An elf named Dubhe seeks a party to rescue the lady Aersent from the pirates of the Savage Sea. Moreover, the party must complete the quest without killing anyone.",
            "A merchant named Amed seeks a party to clear his name against charges of forgery.",
            "A wealthy sage named Monesos seeks a party to slay the Behemoth of Thilda and retrieve its teeth. Moreover, the party must complete the quest without leaving any trace of their involvement.",
            "An ex-adventurer named Arler Yarner seeks a party to slay the Beast of Thariusi and retrieve its hide. Moreover, the party encounters an old ally now working against them.",
            "Somewhere amidst a decaying swamp. A thicket of thorns surrounds a strange monolith of dark crystal",
            "Somewhere on Bear's Approach in the village of Segate, in a nearby realm. The street is strewn with bloody and rotting corpses.",
            "A narrow corridor on the 1st level of the Lost Temple of the Demon Princess. The walls here have been engraved with geometric patterns.",
            "A vault filled with sand on the 2nd level of the Catacombs of Indomitable Ages.",
            "Somewhere amidst a haunted fen. The shadows seem to be alive, and move in the darkness.",
            "A fiery chamber on the 2nd level of the Dark Warrens of Cusada the Eldritch.",
            "Somewhere amidst a decaying fen. The shadows seem to be alive, and move in the darkness.",
            "Somewhere on North Rumoor Way in a nearby city. The street is crowded with drunken brawlers and the town guard.",
            "The common room at the Jester's Tavern inn, in the elven village of Nosea, in a nearby kingdom.",
            "A winding tunnel on the 9th level of the Lair of Adyas.",
            "The Dread Queen cannot be slain until the Spider is broken",
            "The Guardian of Roses shall be slain by the Arrow of Spite when iron is made flesh",
            "The Palace of Tomes shall be found when light becomes shadow and the Golden Gate opens",
            "The Eldest Throne shall be lost when east becomes west and the Blade of Courage is reforged",
            "When the Temple of Ghosts is laid to ruin and the mountains are wreathed in flame, the Sword of Coins shall be restored",
            "The Viridian Castle shall be restored when light becomes shadow",
            "In the Year of Miracles, the Throne of Orbs shall be restored",
            "The Crimson Tyrant cannot be slain until the Venomous Serpent lies in blood and death becomes life",
            "The Forlorn Empress shall reign until the Black Hare rests upon the tomb",
        };

        private static String pick(Random random, String[] values) {
            return values[random.nextInt(values.length)];
        }
        
        private static HVitals fillVitals(Random random, HVitals v) {
            List<Integer> heart = new ArrayList<Integer>();
            List<Integer> coffee = new ArrayList<Integer>();
            for (int i = 0 ; i < 30 ; i++) {
                heart.add(random.nextInt(10));
                coffee.add(random.nextInt(10));
            }
            return v.setHeart(heart).setCoffee(coffee);
        }
        
        static int rankCounter = 1;
        public static HData createRandomData(Random random) {
            List<Hero> ret = new ArrayList<Hero>();
            for (int i = 0 ; i < 100 ; i++) {
                Hero h = GQ.create(Hero.class)
                .setHeight(random.nextInt(100) + 150)
                .setWeight(random.nextInt(100) + 75)
                .setHeroname(pick(random, heroes))
                .setArchenemy(pick(random, heroes))
                .setName(pick(random, forenames) + " " + pick(random, surnames))
                .setIq(random.nextInt(500))
                .setKillcount(random.nextInt(1000))
                .setMission(pick(random, fantasies))
                .setStatus(pick(random, statuses))
                .setRank(rankCounter++)
                .setLocation(GQ.create(HLocation.class)
                        .latitude(random.nextDouble() + 40)
                        .longitude(-(random.nextDouble() + 3)))
                .setVitals(fillVitals(random, GQ.create(HVitals.class)))
                ;
               while (h.getArchenemy().equals(h.getHeroname())) {
                  h.setArchenemy(pick(random, statuses));
               }
               ret.add(h);
            }
            Collections.sort(ret, new Comparator<Hero>() {
                public int compare(Hero o1, Hero o2) {
                    return o1.getRank() - o2.getRank();
                }
            });
            return GQ.create(HData.class).setHeroes(ret);
        }
    }    

}
