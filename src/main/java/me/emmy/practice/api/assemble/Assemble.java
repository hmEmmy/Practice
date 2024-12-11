package me.emmy.practice.api.assemble;

import lombok.Getter;
import lombok.Setter;
import me.emmy.practice.api.assemble.enums.AssembleStyle;
import me.emmy.practice.api.assemble.events.AssembleBoardCreateEvent;
import me.emmy.practice.api.assemble.interfaces.AssembleAdapter;
import me.emmy.practice.api.assemble.listener.AssembleListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter @Setter
public class Assemble {
	private final JavaPlugin plugin;

	private AssembleAdapter adapter;
	private AssembleThread thread;
	private AssembleListener listeners;
	private AssembleStyle assembleStyle = AssembleStyle.MODERN;

	private Map<UUID, AssembleBoard> boards;

	private long ticks = 2;
	private boolean hooked = false, debugMode = true, callEvents = true;

	private final ChatColor[] chatColorCache = ChatColor.values();

	/**
	 * Constructor for the Assemble class.
	 *
	 * @param plugin instance.
	 * @param adapter that is being provided.
	 */
	public Assemble(JavaPlugin plugin, AssembleAdapter adapter) {
		if (plugin == null) {
			throw new RuntimeException("Assemble can not be instantiated without a plugin instance!");
		}

		this.plugin = plugin;
		this.adapter = adapter;
		this.boards = new ConcurrentHashMap<>();

		this.setup();
	}

	/**
	 * Setup Assemble.
	 */
	public void setup() {
		this.listeners = new AssembleListener(this);
		this.plugin.getServer().getPluginManager().registerEvents(this.listeners, this.plugin);

		if (this.thread != null) {
			this.thread.stop();
			this.thread = null;
		}

		for (Player player : this.getPlugin().getServer().getOnlinePlayers()) {
			if (this.isCallEvents()) {
				AssembleBoardCreateEvent createEvent = new AssembleBoardCreateEvent(player);

				Bukkit.getPluginManager().callEvent(createEvent);
				if (createEvent.isCancelled()) {
					continue;
				}
			}

			getBoards().putIfAbsent(player.getUniqueId(), new AssembleBoard(player, this));
		}

		this.thread = new AssembleThread(this);
	}

	public void cleanup() {
		if (this.thread != null) {
			this.thread.stop();
			this.thread = null;
		}

		if (this.listeners != null) {
			HandlerList.unregisterAll(this.listeners);
			this.listeners = null;
		}

		// Destroy player scoreboards.
		for (UUID uuid : getBoards().keySet()) {
			Player player = Bukkit.getPlayer(uuid);

			if (player == null || !player.isOnline()) {
				continue;
			}

			this.boards.remove(uuid);
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
	}
}