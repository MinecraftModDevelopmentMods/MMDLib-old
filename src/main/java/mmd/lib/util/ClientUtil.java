package mmd.lib.util;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ClientUtil {

	private static final float fluidOffset = 0.005F;

	public static void registerModel(Item item, ResourceLocation location) {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		mesher.register(item, 0, new ModelResourceLocation(location, "inventory"));
	}

	public static void registerModel(Block block, ResourceLocation location) {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		mesher.register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(location, "inventory"));
	}

	public static void registerWithMapper(Block block, String modid) {
		if (block != null) {
			final String resourcePath = String.format("%s:%s", modid, block.getRegistryName().getResourcePath());

			ModelLoader.setCustomStateMapper(block, new DefaultStateMapper() {
				@Override
				protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
					return new ModelResourceLocation(resourcePath, getPropertyString(state.getProperties()));
				}
			});

			List<ItemStack> subBlocks = Lists.newArrayList();
			block.getSubBlocks(Item.getItemFromBlock(block), null, subBlocks);

			for (ItemStack stack : subBlocks) {
				IBlockState state = block.getStateFromMeta(stack.getMetadata());
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), stack.getMetadata(), new ModelResourceLocation(resourcePath, Platform.getPropertyString(state.getProperties())));
			}
		}
	}

	public static void registerVariants(Item item, String modid, String... locations) {
		ResourceLocation[] variantArray = new ResourceLocation[locations.length];

		for (int i = 0; i < locations.length; i++) {
			variantArray[i] = new ResourceLocation(modid, item.getUnlocalizedName() + "_" + locations[i]);
		}

		ModelLoader.registerItemVariants(item, variantArray);

		for (int i = 0; i < locations.length; i++) {
			registerModel(item, new ResourceLocation(modid, item.getUnlocalizedName() + "_" + locations[i]));
		}
	}

	public static void registerVariants(Block block, String modid, String... locations) {
		ResourceLocation[] variantArray = new ResourceLocation[locations.length];

		for (int i = 0; i < locations.length; i++) {
			variantArray[i] = new ResourceLocation(modid, block.getUnlocalizedName() + "_" + locations[i]);
		}

		ModelLoader.registerItemVariants(Item.getItemFromBlock(block), variantArray);

		for (int i = 0; i < locations.length; i++) {
			registerModel(block, new ResourceLocation(modid, block.getUnlocalizedName() + "_" + locations[i]));
		}
	}

	public static void setCustomModelLocation(Item item) {
		ModelResourceLocation location = new ModelResourceLocation(item.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(item, 0, location);
	}

	public static void setCustomModelLocation(Block block) {
		ModelResourceLocation location = new ModelResourceLocation(block.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, location);
	}

	public static void renderTexturedQuad(TextureAtlasSprite texture, EnumFacing face, BlockPos pos, double x1, double y1, double z1, double x2, double y2, double z2, int brightness, int color) {
		VertexBuffer renderer = Tessellator.getInstance().getBuffer();
		renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		putTexturedQuad(renderer, texture, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, face, color, brightness, false);
		Tessellator.getInstance().draw();
	}

	public static void renderTexturedCuboid(TextureAtlasSprite[] textures, BlockPos pos, double x1, double y1, double z1, double x2, double y2, double z2, int brightness, int color) {
		VertexBuffer renderer = Tessellator.getInstance().getBuffer();
		renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		putTexturedQuad(renderer, textures[0], x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.DOWN, color, brightness, false);
		putTexturedQuad(renderer, textures[1], x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.NORTH, color, brightness, false);
		putTexturedQuad(renderer, textures[2], x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.EAST, color, brightness, false);
		putTexturedQuad(renderer, textures[3], x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.SOUTH, color, brightness, false);
		putTexturedQuad(renderer, textures[4], x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.WEST, color, brightness, false);
		putTexturedQuad(renderer, textures[5], x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.UP, color, brightness, false);
		Tessellator.getInstance().draw();
	}

	public static void renderTexturedCuboid(TextureAtlasSprite texture, BlockPos pos, double x1, double y1, double z1, double x2, double y2, double z2, int brightness, int color) {
		VertexBuffer renderer = Tessellator.getInstance().getBuffer();
		renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		putTexturedQuad(renderer, texture, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.DOWN, color, brightness, false);
		putTexturedQuad(renderer, texture, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.NORTH, color, brightness, false);
		putTexturedQuad(renderer, texture, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.EAST, color, brightness, false);
		putTexturedQuad(renderer, texture, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.SOUTH, color, brightness, false);
		putTexturedQuad(renderer, texture, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.WEST, color, brightness, false);
		putTexturedQuad(renderer, texture, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.UP, color, brightness, false);
		Tessellator.getInstance().draw();
	}

	public static void renderFluidCuboid(FluidStack fluid, BlockPos pos, double x, double y, double z, double w, double h, double d) {
		double wd = (1d - w) / 2d;
		double hd = (1d - h) / 2d;
		double dd = (1d - d) / 2d;
		renderFluidCuboid(fluid, pos, x, y, z, wd, hd, dd, 1d - wd, 1d - hd, 1d - dd);
	}

	public static void renderFluidCuboid(FluidStack fluid, BlockPos pos, double x, double y, double z, double x1, double y1, double z1, double x2, double y2, double z2) {
		int color = fluid.getFluid().getColor(fluid);
		renderFluidCuboid(fluid, pos, x, y, z, x1, y1, z1, x2, y2, z2, color);
	}

	public static void renderFluidCuboid(FluidStack fluid, BlockPos pos, double x, double y, double z, double x1, double y1, double z1, double x2, double y2, double z2, int color) {
		VertexBuffer renderer = Tessellator.getInstance().getBuffer();
		renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		int brightness = Minecraft.getMinecraft().world.getCombinedLight(pos, fluid.getFluid().getLuminosity());
		TextureAtlasSprite still = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(fluid.getFluid().getStill(fluid).toString());
		TextureAtlasSprite flowing = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(fluid.getFluid().getFlowing(fluid).toString());
		putTexturedQuad(renderer, still, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.DOWN, color, brightness, false);
		putTexturedQuad(renderer, flowing, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.NORTH, color, brightness, true);
		putTexturedQuad(renderer, flowing, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.EAST, color, brightness, true);
		putTexturedQuad(renderer, flowing, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.SOUTH, color, brightness, true);
		putTexturedQuad(renderer, flowing, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.WEST, color, brightness, true);
		putTexturedQuad(renderer, still, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.UP, color, brightness, false);
		Tessellator.getInstance().draw();
	}

	public static void renderStackedFluidCuboid(FluidStack fluid, double px, double py, double pz, BlockPos pos, BlockPos from, BlockPos to, double ymin, double ymax) {
		if (ymin >= ymax) {
			return;
		}

		VertexBuffer renderer = Tessellator.getInstance().getBuffer();
		renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		int color = fluid.getFluid().getColor(fluid);
		int brightness = Minecraft.getMinecraft().world.getCombinedLight(pos, fluid.getFluid().getLuminosity());
		GlStateManager.translate(from.getX(), from.getY(), from.getZ());
		TextureAtlasSprite still = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(fluid.getFluid().getStill(fluid).toString());
		TextureAtlasSprite flowing = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(fluid.getFluid().getFlowing(fluid).toString());

		if (still == null) {
			still = Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
		}

		if (flowing == null) {
			flowing = Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
		}

		int xd = to.getX() - from.getX();
		int yd = (int) (ymax - ymin);
		int zd = to.getZ() - from.getZ();
		double xmin = fluidOffset;
		double xmax = xd + 1d - fluidOffset;
		double zmin = fluidOffset;
		double zmax = zd + 1d - fluidOffset;
		double[] xs = new double[2 + xd];
		double[] ys = new double[2 + yd];
		double[] zs = new double[2 + zd];

		xs[0] = xmin;

		for (int i = 1; i <= xd; i++) {
			xs[i] = i;
		}

		xs[xd + 1] = xmax;

		ys[0] = ymin;

		for (int i = 1; i <= yd; i++) {
			ys[i] = i;
		}

		ys[yd + 1] = ymax;

		zs[0] = zmin;

		for (int i = 1; i <= zd; i++) {
			zs[i] = i;
		}

		zs[zd + 1] = zmax;

		for (int y = 0; y <= yd; y++) {
			for (int z = 0; z <= zd; z++) {
				for (int x = 0; x <= xd; x++) {
					double x1 = xs[x];
					double x2 = xs[x + 1] - x1;
					double y1 = ys[y];
					double y2 = ys[y + 1] - y1;
					double z1 = zs[z];
					double z2 = zs[z + 1] - z1;

					if (x == 0) {
						putTexturedQuad(renderer, flowing, x1, y1, z1, x2, y2, z2, EnumFacing.WEST, color, brightness, true);
					}
					if (x == xd) {
						putTexturedQuad(renderer, flowing, x1, y1, z1, x2, y2, z2, EnumFacing.EAST, color, brightness, true);
					}
					if (y == 0) {
						putTexturedQuad(renderer, still, x1, y1, z1, x2, y2, z2, EnumFacing.DOWN, color, brightness, false);
					}
					if (y == yd) {
						putTexturedQuad(renderer, still, x1, y1, z1, x2, y2, z2, EnumFacing.UP, color, brightness, false);
					}
					if (z == 0) {
						putTexturedQuad(renderer, flowing, x1, y1, z1, x2, y2, z2, EnumFacing.NORTH, color, brightness, true);
					}
					if (z == zd) {
						putTexturedQuad(renderer, flowing, x1, y1, z1, x2, y2, z2, EnumFacing.SOUTH, color, brightness, true);
					}
				}
			}
		}

		Tessellator.getInstance().draw();
	}

	public static void putTexturedQuad(VertexBuffer renderer, TextureAtlasSprite sprite, double x, double y, double z, double w, double h, double d, EnumFacing face, int color, int brightness, boolean flowing) {
		int l1 = brightness >> 0x10 & 0xFFFF;
		int l2 = brightness & 0xFFFF;
		int a = color >> 24 & 0xFF;
		int r = color >> 16 & 0xFF;
		int g = color >> 8 & 0xFF;
		int b = color & 0xFF;
		putTexturedQuad(renderer, sprite, x, y, z, w, h, d, face, r, g, b, a, l1, l2, flowing);
	}

	public static void putTexturedQuad(VertexBuffer renderer, TextureAtlasSprite sprite, double x, double y, double z, double w, double h, double d, EnumFacing face, int r, int g, int b, int a, int light1, int light2, boolean flowing) {
		if (sprite == null) {
			return;
		}

		double minU;
		double maxU;
		double minV;
		double maxV;
		double size = 16f;

		if (flowing) {
			size = 8f;
		}

		double x1 = x;
		double x2 = x + w;
		double y1 = y;
		double y2 = y + h;
		double z1 = z;
		double z2 = z + d;
		double xt1 = x1 % 1d;
		double xt2 = xt1 + w;

		while (xt2 > 1f) {
			xt2 -= 1f;
		}

		double yt1 = y1 % 1d;
		double yt2 = yt1 + h;

		while (yt2 > 1f) {
			yt2 -= 1f;
		}

		double zt1 = z1 % 1d;
		double zt2 = zt1 + d;

		while (zt2 > 1f) {
			zt2 -= 1f;
		}

		if (flowing) {
			double tmp = 1d - yt1;
			yt1 = 1d - yt2;
			yt2 = tmp;
		}

		switch (face) {
			case DOWN:
			case UP:
				minU = sprite.getInterpolatedU(xt1 * size);
				maxU = sprite.getInterpolatedU(xt2 * size);
				minV = sprite.getInterpolatedV(zt1 * size);
				maxV = sprite.getInterpolatedV(zt2 * size);
				break;
			case NORTH:
			case SOUTH:
				minU = sprite.getInterpolatedU(xt2 * size);
				maxU = sprite.getInterpolatedU(xt1 * size);
				minV = sprite.getInterpolatedV(yt1 * size);
				maxV = sprite.getInterpolatedV(yt2 * size);
				break;
			case WEST:
			case EAST:
				minU = sprite.getInterpolatedU(zt2 * size);
				maxU = sprite.getInterpolatedU(zt1 * size);
				minV = sprite.getInterpolatedV(yt1 * size);
				maxV = sprite.getInterpolatedV(yt2 * size);
				break;
			default:
				minU = sprite.getMinU();
				maxU = sprite.getMaxU();
				minV = sprite.getMinV();
				maxV = sprite.getMaxV();
		}

		switch (face) {
			case DOWN:
				renderer.pos(x1, y1, z1).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
				renderer.pos(x2, y1, z1).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
				renderer.pos(x2, y1, z2).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
				renderer.pos(x1, y1, z2).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
				break;
			case UP:
				renderer.pos(x1, y2, z1).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
				renderer.pos(x1, y2, z2).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
				renderer.pos(x2, y2, z2).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
				renderer.pos(x2, y2, z1).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
				break;
			case NORTH:
				renderer.pos(x1, y1, z1).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
				renderer.pos(x1, y2, z1).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
				renderer.pos(x2, y2, z1).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
				renderer.pos(x2, y1, z1).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
				break;
			case SOUTH:
				renderer.pos(x1, y1, z2).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
				renderer.pos(x2, y1, z2).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
				renderer.pos(x2, y2, z2).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
				renderer.pos(x1, y2, z2).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
				break;
			case WEST:
				renderer.pos(x1, y1, z1).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
				renderer.pos(x1, y1, z2).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
				renderer.pos(x1, y2, z2).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
				renderer.pos(x1, y2, z1).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
				break;
			case EAST:
				renderer.pos(x2, y1, z1).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
				renderer.pos(x2, y2, z1).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
				renderer.pos(x2, y2, z2).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
				renderer.pos(x2, y1, z2).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
				break;
		}
	}

}