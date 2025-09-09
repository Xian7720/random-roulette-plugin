package io.github.xian7720.randomroulette;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class RandomRoulette implements CommandExecutor
{

    private static final Logger log = LoggerFactory.getLogger(RandomRoulette.class);

    private static final Random random = new Random();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings)
    {
        var player = Bukkit.getPlayer("rf7720");
        var world = player.getWorld();

        if(strings.length > 0){
            switch(strings[0]){
                case "1":
                    spawnCreeper(world, player);
                    return true;
                case "2":
                    getPoison(player);
                    return true;
                case "6":
                    setChangeHealth(player);
                    return true;
                case "9":
                    setDurabilityOne(player);
                    return true;
                case "10":
                    randomReplaceItem(player);
                    return true;
                case "11":
                    reverseGravity(player);
                    return true;
                default:
                    return false;
            }
        }
        else{
            int num = random.nextInt(1000);

            if(num <= 300) player.sendMessage("꽝!!"); // 30%
            else if(num <= 340) spawnCreeper(world, player); // 4%
            else if(num <= 380) getPoison(player); // 4%
            else if(num <= 420) getBlind(player); // 4%
            else if(num <= 480) getDirt(player); // 6%
            else if(num <= 490) getWither(player); // 1%
            else if(num <= 570) setChangeHealth(player); // 8%
            else if(num <= 600) summonLightning(world, player); // 3%
            else if(num <= 650) flyingFirework(world, player); // 5%
            else if(num <= 690) setDurabilityOne(player); // 4%
            else if(num <= 730) randomReplaceItem(player); // 4%
            else if(num <= 760) randomReplaceItem(player); // 3%

//        Objects.requireNonNull(player).sendMessage("Random number: " + num);

            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

            return true;
        }
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
        p.sendMessage("번개 또는 전정(電霆) 현상은 구름과 구름, 구름과 지표면 사이에서 전기의 방전이 일어나는 현상이다.");
    }

    public void getWither(Player p)
    {
        var potionEffect = new PotionEffect(PotionEffectType.WITHER, 2007831, 0);
        p.addPotionEffect(potionEffect);
        p.sendMessage("죽기까지 10... 9... 8...");
    }

    public void setChangeHealth(Player p)
    {

        var attr = p.getAttribute(Attribute.MAX_HEALTH);
        if(attr == null) return;

        double crtHealth = attr.getBaseValue();
        double newHealth = 0;

        int r = random.nextInt(2);
        if(r == 1 && crtHealth > 1){
            newHealth = Math.abs(crtHealth / 2);
            p.sendMessage("절반의 체력!");
        }
        else if(crtHealth < 160){
            newHealth = crtHealth * 2;
            p.sendMessage("두배의 체력!");
        }
        else{
            p.sendMessage("최대 체력 값을 초과했습니다. (변동 없음)");
            return;
        }

        attr.setBaseValue(newHealth);

        p.sendMessage("현재 최대 체력: " + newHealth);
    }

    public void flyingFirework(World w, Player p)
    {
        var effect = FireworkEffect.builder().withColor(Color.WHITE).withFade(Color.BLACK).flicker(true).with(FireworkEffect.Type.CREEPER);
        var fw = w.spawn(p.getLocation(), Firework.class);
        var meta = fw.getFireworkMeta();

        meta.addEffect(effect.build());
        meta.setPower(3);

        fw.setFireworkMeta(meta);
        fw.addPassenger(p);

        p.sendMessage("내리기 키는 Shift입니다.");
    }

    public void setDurabilityOne(Player p)
    {
        var inventory = p.getInventory();

        for(ItemStack item : inventory.getContents()){
            if(item == null || item.getType().isAir() || item.getType().getMaxDurability() == 0) continue;

            if(item.getItemMeta() instanceof Damageable dmg){
                dmg.setDamage(item.getType().getMaxDurability() - 1);
                item.setItemMeta(dmg);
            }
        }

        p.sendMessage("모든 아이템 체력 1!");
    }

    public void randomReplaceItem(Player p)
    {
        var inventory = p.getInventory();
        var newItem = Material.PUFFERFISH;
        int cnt = 0;

        ItemStack[] contents = inventory.getContents();
        for(int i = 0; i < contents.length; i++){
            ItemStack item = contents[i];
            if(item == null || item.getType().isAir() || item.getType() == newItem) continue;


            if(random.nextInt(10) == 7){
                cnt++;
                inventory.setItem(i, new ItemStack(newItem));
            }
        }

        p.sendMessage("총 " + cnt + "개의 아이템이 복어가 되었습니다!");
    }

    public void reverseGravity(Player p)
    {
        var attr = p.getAttribute(Attribute.GRAVITY);
        attr.setBaseValue(-0.01);
        p.sendMessage("↑↑↑");
        Bukkit.getScheduler().runTaskLater(RandomRoulettePlugin.getPlugin(RandomRoulettePlugin.class), () -> {
            attr.setBaseValue(0.08);
            p.sendMessage("↓↓↓");
        }, 600L);
    }
}
