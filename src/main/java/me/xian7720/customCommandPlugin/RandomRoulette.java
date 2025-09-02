package me.xian7720.customCommandPlugin;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class RandomRoulette implements CommandExecutor {

    private static final Logger log = LoggerFactory.getLogger(RandomRoulette.class);

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        var world = commandSender.getServer().getWorlds().getFirst();
        var player = Bukkit.getPlayer("rf7720");

        int num = rollRandom();

        if (num <= 300) player.sendMessage("꽝!"); // 30%
        else if (num <= 340) spawnCreeper(world, player); // 4%
        else if (num <= 380) getPoison(player); // 4%
        else if (num <= 420) getBlind(player); // 4%
        else if (num <= 500) getDirt(player); // 8%
        else if (num <= 510) getWither(player); // 1%
        else if (num <= 590) setChangeHealth(player); // 8%
        else if (num <= 620) summonLightning(world, player); // 3%
        else if (num <= 670) FlyingFirework(world, player); // 5%











//        Objects.requireNonNull(player).sendMessage("Random number: " + num);


        return false;
    }
        public int rollRandom ()
        {
            Random random = new Random();
            return random.nextInt(1000);
        }

        public void spawnCreeper(World w, Player p)
        {
            w.spawn(p.getLocation(), Creeper.class);
            p.sendMessage("*펑*");
        }

        public void getPoison(Player p)
        {
            var potionEffect = new PotionEffect(PotionEffectType.POISON, 400, 0);
            p.addPotionEffect(potionEffect);
            p.sendMessage("독에걸렷다");
        }

        public void getBlind(Player p)
        {
            var potionEffect = new PotionEffect(PotionEffectType.BLINDNESS, 200, 0);
            p.addPotionEffect(potionEffect);
            p.sendMessage("넌이제앞이안보인다");
        }

        public void getDirt(Player p)
        {
            var item = new ItemStack(Material.DIRT, 2000);
            p.getInventory().addItem(item);
            p.sendMessage("흙 X 2000!!!");
        }

        public void summonLightning(World w, Player p)
        {
            w.strikeLightning(p.getLocation());
            p.sendMessage("번개 또는 전정(電霆) 현상은 구름과 구름, 구름과 지표면 사이에서 전기의 방전이 일어나는 현상이다." + "\n" + "번개가 치면 충격파와 함께 공기의 파열음이 들리는데, 이를 '천둥' 또는 '우레'라 부른다.\n" +
                    "\n" +
                    "구름과 지표면 사이에서 발생한 번개를 벼락 혹은 '낙뢰'라고 한다.");
        }

        public void getWither(Player p)
        {
            var potionEffect = new PotionEffect(PotionEffectType.WITHER, 831, 127);
            p.addPotionEffect(potionEffect);
            p.sendMessage("죽기까지 10... 9... 8...");
        }

        public void setChangeHealth(Player p) {
        Random random = new Random();

        var attr = p.getAttribute(Attribute.MAX_HEALTH);
        if (attr == null) return;

        double crtHealth = attr.getBaseValue();
        double newHealth;

        if (random.nextInt(2) == 1) {
            newHealth = crtHealth * 2;
            p.sendMessage("두배의 체력!");
        } else {
            newHealth = crtHealth / 2;
            p.sendMessage("절반의 체력!");
        }

        attr.setBaseValue(newHealth);

        if (p.getHealth() > newHealth) {
            p.setHealth(newHealth);
        }

        p.sendMessage("현재 최대 체력: " + newHealth);
    }

    public void FlyingFirework(World w, Player p)
    {
        var effect = FireworkEffect.builder().withColor(Color.WHITE).withFade(Color.BLACK).flicker(true).with(FireworkEffect.Type.CREEPER);
        var fw = w.spawn(p.getLocation(), Firework.class);
        var meta = fw.getFireworkMeta();

        meta.addEffect(effect.build());
        meta.setPower(3);

        fw.setFireworkMeta(meta);
        fw.addPassenger(p);

        p.sendMessage("푸슝~ 펑 와그작 와그작");
    }

}
