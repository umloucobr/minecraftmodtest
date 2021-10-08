package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class SCombatPacket implements IPacket<IClientPlayNetHandler> {
   public SCombatPacket.Event eventType;
   public int playerId;
   public int entityId;
   public int duration;
   public ITextComponent deathMessage;

   public SCombatPacket() {
   }

   public SCombatPacket(CombatTracker tracker, SCombatPacket.Event eventIn) {
      this(tracker, eventIn, StringTextComponent.EMPTY);
   }

   public SCombatPacket(CombatTracker tracker, SCombatPacket.Event event, ITextComponent deathMessage) {
      this.eventType = event;
      LivingEntity livingentity = tracker.getBestAttacker();
      switch(event) {
      case END_COMBAT:
         this.duration = tracker.getCombatDuration();
         this.entityId = livingentity == null ? -1 : livingentity.getEntityId();
         break;
      case ENTITY_DIED:
         this.playerId = tracker.getFighter().getEntityId();
         this.entityId = livingentity == null ? -1 : livingentity.getEntityId();
         this.deathMessage = deathMessage;
      }

   }

   /**
    * Reads the raw packet data from the data stream.
    */
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.eventType = buf.readEnumValue(SCombatPacket.Event.class);
      if (this.eventType == SCombatPacket.Event.END_COMBAT) {
         this.duration = buf.readVarInt();
         this.entityId = buf.readInt();
      } else if (this.eventType == SCombatPacket.Event.ENTITY_DIED) {
         this.playerId = buf.readVarInt();
         this.entityId = buf.readInt();
         this.deathMessage = buf.readTextComponent();
      }

   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeEnumValue(this.eventType);
      if (this.eventType == SCombatPacket.Event.END_COMBAT) {
         buf.writeVarInt(this.duration);
         buf.writeInt(this.entityId);
      } else if (this.eventType == SCombatPacket.Event.ENTITY_DIED) {
         buf.writeVarInt(this.playerId);
         buf.writeInt(this.entityId);
         buf.writeTextComponent(this.deathMessage);
      }

   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void processPacket(IClientPlayNetHandler handler) {
      handler.handleCombatEvent(this);
   }

   public boolean shouldSkipErrors() {
      return this.eventType == SCombatPacket.Event.ENTITY_DIED;
   }

   public static enum Event {
      ENTER_COMBAT,
      END_COMBAT,
      ENTITY_DIED;
   }
}