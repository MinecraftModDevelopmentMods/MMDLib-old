package mmd.lib.client.model;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Shape3D
{
	public Shape3D(PositionTransformVertex[] verts, TexturedPolygon[] poly)
	{
		vertices = verts;
		faces = poly;
	}
	public PositionTransformVertex[] vertices;
	public TexturedPolygon[] faces;
}
