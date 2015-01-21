package com.vaadin.prototype.wc.gwt.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.builders.JsonBuilder;

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

        Hero setPhotoUrl(String s);

        String getPhotoUrl();
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

    // The number or elements in the 'names', 'photoUrls' and 'heroes' arrays
    // must match
    public static class MockData {

        private static String[] actions = { "Fix", "Implement", "Disable",
                "Activate", "Design", "Export", "Import", "Produce", "Invent",
                "Establish", "Feed", "Launch", "Deliver", "Polish" };

        private static String[] targets = { "Grid", "world peace",
                "teleportation", "Vaadin", "the dog", "the weather", "soup",
                "results" };

        private static String[] names = { "Joonas Lehtinen", "Jurka Rahikkala",
                "Jani Laakso", "Marc Englund", "Sami Ekblad", "Henri Muurimaa",
                "Ville Ingman", "Mikael Vappula", "Tomi Virtanen",
                "Artur Signell", "Jouni Koivuviita", "Matti Tahvonen",
                "Jonatan Kronqvist", "Hannu Salonen", "Henri Kerola",
                "Teemu Pöntelin", "Johannes Tuikkala", "Risto Yrjänä",
                "Kim Leppänen", "Jonas Granvik", "Jens Jansson", "Henrik Paul",
                "John Ahlroos", "Thomas Mattsson", "Fredrik Rönnlund",
                "Teppo Kurki", "Henri Sara", "Sebastian Nyholm", "Peter Lehto",
                "Tomi Virkki", "Mikael Grankvist", "Petter Holmström",
                "Marcus Hellberg", "Petri Heinonen", "Matti Vesa",
                "Anna Koskinen", "Marlon Richert", "Jarno Rantala",
                "Marko Grönroos", "Johannes Häyry", "Leif Åstrand",
                "Sami Kaksonen", "Johannes Dahlström", "Samuli Penttilä",
                "Rolf Smeds", "Sami Viitanen", "Pekka Hyvönen", "Tapio Aali",
                "Haijian Wang", "Mikolaj Olszewski", "Tanja Repo",
                "Pekka Perälä", "Jonni Nakari", "Denis Anisimov",
                "Amir Al-Majdalawi", "Patrik Lindström", "Michael Tzukanov",
                "Matti Hosio", "Juho Nurminen", "Johannes Eriksson",
                "Joacim Päivärinne", "Felype Ferreira", "Mika Murtojärvi",
                "Artem Godin", "Maciej Przepiora", "Teemu Suo-Anttila",
                "Minna Hohti", "Olli Helttula", "Sauli Tähkäpää",
                "Manuel Carrasco", "Jarmo Kemppainen", "Juuso Valli",
                "Henrik Skogström", "Dmitrii Rogozin", "Markus Koivisto",
                "Bogdan Udrescu", "Heikki Ohinmaa", "Guillermo Alvarez",
                "Sergey Budkin", "Kari Kaitanen" };

        private static String[] heroes = { "The Nuclear Veteran",
                "The Elegent Gunner", "The Aqua Fighter", "The Swift Whiz",
                "Master Clever Wolverine", "Cool Fighter",
                "Professor Dark Genius", "Godly Wasp", "Voiceless Stranger",
                "Silver Goliath", "The Jolly Ibis", "The Storm Snipe",
                "The Brass Wolf", "The Majestic Seer", "Master Long Slayer",
                "Earth Hawk", "Purple Mothman", "Agent Accidental Wasp",
                "Thornhead", "Death Roach", "The Old Phoenix",
                "The Outrageous Bear", "The Hypnotic Warden",
                "The Ancient Sentinel", "Bronze Gloom", "Agent Gentle Cheetah",
                "Accidental Daggers", "Doctor Infamous Grasshopper",
                "Ice Raven", "The Green Scout", "The Fire Hammer",
                "The Nuclear Phoenix", "The Copper Watcher", "Orange Guardian",
                "Cold Dagger", "Doctor Fantastic Wasp", "Silver Protector",
                "Fallen Pheonix", "Cleanser", "The Royal Sparrow",
                "The Vengeful Hammer", "The Dramatic Watcher",
                "The Mammoth Champion", "Lord Impossible Wasp",
                "Master Silver Fighter", "Doctor Steel Owl", "Red Fighter",
                "Mister Penance", "The Aqua Doctor", "The Glorious Monarch",
                "The Golden Angel", "The Quick Monarch",
                "Warden Marked Prophet", "Honorable Slayer", "Brass Tiger",
                "Lord Terrific Spider", "Mister Y", "Solar Flare",
                "The Brave Spirit", "The Fabulous Eagle",
                "The Honorable Sparrow", "The Gray Bear",
                "Professor Golden Scepter", "Fiery Shepherd",
                "Gigantic Mothman", "Kind Magician", "Deadnite", "Starbright",
                "The Magnificent Axeman", "The Dark Prince",
                "The Impossible Starling", "The Mysterious Nightowl",
                "Warden Gentle Grasshopper", "Mister Kind Shade",
                "Master Royal Mastermind", "Light Spectacle",
                "The Iron Spectacle", "The Hypnotic Mole",
                "The Electric Smasher", "The Righteous Swordsman" };

        static String[] photoUrls = {
                "https://vaadin.com/vaadin-theme/images/company/personnel/joonas.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/jurka.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/jani.png",
                "/image/image_gallery?uuid=9977b966-19c7-49d1-a8ae-4ee57ce934d3&groupId=10187&t=1360067298350",
                "https://vaadin.com/vaadin-theme/images/company/personnel/sami.png",
                "https://vaadin.com/documents/10187/2497140/henrim.jpg/5d957694-7025-49cd-a8ce-6ca0f5e1985e?t=1409139651906",
                "https://vaadin.com/vaadin-theme/images/company/personnel/ville.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/mikael.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/tomivirtanen.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/artur.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/jouni.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/matti.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/jonatan.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/hannu.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/hene.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/teemu.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/johannes.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/risto.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/kim.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/jonas.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/jens.png",
                "https://vaadin.com/documents/10187/2497140/henrikpaul.jpg/d9d0da78-8e8c-4142-b0c9-6bb991ad38f3?t=1411976283985",
                "https://vaadin.com/vaadin-theme/images/company/personnel/john.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/thomas.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/fredu.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/teppo.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/hesara.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/sebastian.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/peter.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/virkki.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/mgrankvi.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/petter.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/marcushellberg.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/petri.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/mattivesa.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/anna.png",
                "https://vaadin.com/documents/10187/2497140/marlon.jpg/fa9e4026-25ed-44d7-a4c8-477b8548d42e?t=1409139668340",
                "https://vaadin.com/vaadin-theme/images/company/personnel/jarno.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/magi.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/johku.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/leif.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/samik.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/j_dahlstrom.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/samuli.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/rofa.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/alump.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/pekka.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/tapio.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/haijian.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/miki.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/tanja.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/pekka.perala.png",
                "https://vaadin.com/vaadin-theme/images/company/personnel/jonni.png",
                "https://vaadin.com/documents/10187/2497140/denis.png/195f599d-64f9-4c8e-96ea-32c9006ce447?t=1402995006000",
                "https://vaadin.com/image/image_gallery?uuid=36db0405-7d1e-41e5-b315-c42ffc38ef78&groupId=10187&t=1362992325210",
                "https://vaadin.com/documents/10187/2497140/partik.png/31546700-1685-497b-b8b2-7b75d154d9c0?t=1402995211000",
                "https://vaadin.com/documents/10187/2497140/michaelt.png/abe07768-d538-4995-b5a6-6780dc673923?t=1402995152000",
                "https://vaadin.com/documents/10187/2497140/mattihosio.png/d4136ed9-7e7b-4c00-9f47-652a533e0ebe?t=1402995141000",
                "https://vaadin.com/documents/10187/2497140/juho.png/1a218c3f-5070-4afe-b0e4-1003433caf68?t=1402995086000",
                "https://vaadin.com/documents/10187/2497140/johanneseriksson.png/f3c7710a-0dc1-4999-acb8-aa0cc12fcc86?t=1402995074000",
                "https://vaadin.com/documents/10187/2497140/joacim.png/7caceb96-3b5b-4629-8d4f-ed2b9905759d?t=1402995062000",
                "https://vaadin.com/documents/10187/2497140/felype.png/dd245057-78d7-4849-8eb7-b4a8d24b4aa2?t=1402995028000",
                "https://vaadin.com/documents/10187/2497140/mika.png/989dbd3e-de0a-4c4e-bcd9-f71bbc9c957a?t=1402995163000",
                "https://vaadin.com/documents/10187/2497140/artem.png/9f1a4ed1-d8e2-433e-bcc5-ba0c3e3bc477?t=1402994994000",
                "https://vaadin.com/documents/10187/2497140/maciej.png/6add5593-bd7b-4899-a40d-5a2cad4ae231?t=1402995107000",
                "https://vaadin.com/documents/10187/2497140/teemus.png/d714cd8b-4efa-47cf-80f2-4a1d6e4115cc?t=1402995230000",
                "https://vaadin.com/documents/10187/2497140/minna.png/e4b2dc1e-d5c0-4994-a17f-64b5f75c3e2e?t=1402995174000",
                "https://vaadin.com/documents/10187/2497140/olli.png/e14b703a-6a08-4a85-be07-562ffabf8795?t=1402995184000",
                "https://vaadin.com/documents/10187/2497140/sauli.png/e3093d43-f520-41c0-bd72-492277df4e0d?t=1402995221000",
                "https://vaadin.com/documents/10187/2497140/manolo.png/41987ded-9091-4dd6-b7f3-411c720839fa?t=1402995118000",
                "https://vaadin.com/documents/10187/2497140/jarmo.png/1b32543e-71b5-422b-bf8f-49ebf14e9bba?t=1402995052000",
                "https://vaadin.com/documents/10187/2497140/juuso.png/f0a8d15b-b742-4618-b915-f3e54060bcaf?t=1402995096000",
                "https://vaadin.com/documents/10187/2497140/henrik.png/28627608-7693-4b5d-8d9c-6fbcd5a567f9?t=1402995040000",
                "https://vaadin.com/documents/10187/2497140/dmitrii.png/42eec4d3-2444-4599-a38c-cfb3cbb72b9b?t=1402995017000",
                "https://vaadin.com/documents/10187/2497140/markus.png/32c8875e-3b60-4f34-8262-94c88fbd27df?t=1402995129000",
                "https://vaadin.com/documents/10187/2497140/bogdan.png/f090574d-f64d-4bf2-bfe5-66924446eb5a?t=1403872722223",
                "https://vaadin.com/documents/10187/2497140/heikki.jpg/c604d026-7b24-4535-84e2-13bf9c578724?t=1409139638635",
                "https://vaadin.com/documents/10187/2497140/guillermo.jpg/8aec29fd-ff4d-4986-9105-9f5cc84d8209?t=1409139610000",
                "https://vaadin.com/documents/10187/2497140/sergey.jpg/f02f4014-9df9-4407-8669-228ca480f2de?t=1411377814055",
                "https://vaadin.com/documents/10187/2497140/kari.jpg/5cf382f5-4288-44d8-83fd-f9304c08970c?t=1412253599160" };

        static String[] statuses = { "Vacation", "On a Mission", "Shopping",
                "Hanging out", "On a Mission", "Sleeping", "Exhausted",
                "On a Quest", "Trainning", "Terminated", };

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
                "The Forlorn Empress shall reign until the Black Hare rests upon the tomb", };

        private static String pick(Random random, String[] values) {
            return values[random.nextInt(values.length)];
        }

        private static HVitals fillVitals(Random random, HVitals v) {
            List<Integer> heart = new ArrayList<Integer>();
            List<Integer> coffee = new ArrayList<Integer>();
            for (int i = 0; i < 30; i++) {
                heart.add(random.nextInt(10));
                coffee.add(random.nextInt(10));
            }
            return v.setHeart(heart).setCoffee(coffee);
        }

        static int rankCounter = 1;

        public static HData createRandomData(Random random) {
            List<Hero> ret = new ArrayList<Hero>();
            for (int i = 0; i < names.length; i++) {
                Hero h = GQ
                        .create(Hero.class)
                        .setHeight(random.nextInt(100) + 150)
                        .setWeight(random.nextInt(100) + 75)
                        .setHeroname(heroes[i])
                        .setArchenemy(heroes[heroes.length - i - 1])
                        .setName(names[i])
                        .setIq(random.nextInt(500))
                        .setKillcount(random.nextInt(1000))
                        .setMission(pick(random, fantasies))
                        .setStatus(pick(random, statuses))
                        .setRank(rankCounter++)
                        .setLocation(
                                GQ.create(HLocation.class)
                                        .latitude(random.nextDouble() + 40)
                                        .longitude(-(random.nextDouble() + 3)))
                        .setVitals(fillVitals(random, GQ.create(HVitals.class)))
                        .setPhotoUrl(photoUrls[i]);
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
