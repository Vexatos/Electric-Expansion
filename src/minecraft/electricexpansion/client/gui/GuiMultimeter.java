package electricexpansion.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;

import org.lwjgl.opengl.GL11;

import universalelectricity.core.electricity.ElectricInfo;
import universalelectricity.core.electricity.ElectricInfo.ElectricUnit;
import universalelectricity.prefab.network.PacketManager;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.network.PacketDispatcher;
import electricexpansion.ElectricExpansion;
import electricexpansion.containers.ContainerMultimeter;
import electricexpansion.tile.TileEntityMultimeter;

@SideOnly(Side.CLIENT)
public class GuiMultimeter extends GuiContainer
{
	private TileEntityMultimeter tileEntity;

	private int containerWidth;
	private int containerHeight;

	public GuiMultimeter(TileEntityMultimeter MultiMeter)
	{
		super(new ContainerMultimeter(MultiMeter));
		this.tileEntity = MultiMeter;
	}

	public void initGui()
	{
		PacketDispatcher.sendPacketToServer(PacketManager.getPacket(ElectricExpansion.CHANNEL, this.tileEntity, (int) -1, true));
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
		PacketDispatcher.sendPacketToServer(PacketManager.getPacket(ElectricExpansion.CHANNEL, this.tileEntity, (int) -1, false));
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRenderer.drawString(this.tileEntity.getInvName(), 15, 6, 4210752);
		this.fontRenderer.drawString(ElectricInfo.getDisplay(tileEntity.electricityReading.voltage, ElectricUnit.VOLTAGE), (this.width - (this.xSize - 130)) / 2, (this.height - (this.ySize - 80)) / 2, 0x000000);
		this.fontRenderer.drawString(ElectricInfo.getDisplay(tileEntity.electricityReading.amperes, ElectricUnit.AMPERE), (this.width - (this.xSize - 130)) / 2, (this.height - (this.ySize - 60)) / 2, 0x000000);

	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		int var4 = this.mc.renderEngine.getTexture("/electricexpansion/textures/mattredsox/VoltDetectorGUI.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(var4);

		containerWidth = (this.width - this.xSize) / 2;
		containerHeight = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
	}
}
