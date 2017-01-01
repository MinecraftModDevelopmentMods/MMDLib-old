package com.mcmoddev.lib.client.model;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @Author Ferdinand (FEX___96)
 */
@SideOnly(Side.CLIENT)
public class Tessellator extends net.minecraft.client.renderer.Tessellator {

    public static Tessellator INSTANCE = new Tessellator();
    private static ByteBuffer bbuf = GLAllocation.createDirectByteBuffer(0x200000 * 4);
    private static FloatBuffer fbuf = bbuf.asFloatBuffer();
    private static ShortBuffer sbuf = bbuf.asShortBuffer();
    private static IntBuffer ibuf = bbuf.asIntBuffer();
    private int rbs = 0, verts = 0, br, c, rbi = 0, vertices = 0, dm, n;
    private boolean ht = false, in = false, drawing = false;
    private double u, v, w, x_o, y_o, z_o;
    private int[] rb;

    public Tessellator() {
        super(2097152);
    }

    public static Tessellator getInstance () {
        return INSTANCE;
    }

    public void startDrawing (int i) {
        if (!this.drawing) {
            this.drawing = true;
            this.dm = i;
            this.in = this.ht = false;
            this.reset();
        }
    }

    @Override
    public void draw () {
        if (this.drawing) {
            this.drawing = false;
            int o = 0;
            while (o < this.verts) {
                final int vtc = Math.min(this.verts - o, 0x200000 >> 5);
                ibuf.clear();
                ibuf.put(this.rb, o * 10, vtc * 10);
                bbuf.position(0);
                bbuf.limit(vtc * 40);
                o += vtc;
                if (this.ht) {
                    fbuf.position(3);
                    GL11.glTexCoordPointer(4, 40, fbuf);
                    GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                }
                if (this.in) {
                    bbuf.position(32);
                    GL11.glNormalPointer(40, bbuf);
                    GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
                }
                fbuf.position(0);
                GL11.glVertexPointer(3, 40, fbuf);
                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
                GL11.glDrawArrays(this.dm, 0, vtc);
                GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
                if (this.ht)
                    GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                if (this.in)
                    GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
            }
            if (this.rbs > 0x20000 && this.rbi < this.rbs << 3) {
                this.rbs = 0;
                this.rb = null;
            }
            this.reset();
            return;
        }
    }

    private void reset () {
        this.verts = this.rbi = this.vertices = 0;
        bbuf.clear();
    }

    public void addVertex (double par1, double par3, double par5) {
        if (this.rbi >= this.rbs - 40)
            if (this.rbs == 0) {
                this.rbs = 0x10000;
                this.rb = new int[this.rbs];
            }
            else {
                this.rbs *= 2;
                this.rb = Arrays.copyOf(this.rb, this.rbs);
            }
        if (this.ht) {
            this.rb[this.rbi + 3] = Float.floatToRawIntBits((float) this.u);
            this.rb[this.rbi + 4] = Float.floatToRawIntBits((float) this.v);
            this.rb[this.rbi + 5] = Float.floatToRawIntBits(0.0F);
            this.rb[this.rbi + 6] = Float.floatToRawIntBits((float) this.w);
        }
        if (this.in)
            this.rb[this.rbi + 8] = this.n;
        this.rb[this.rbi] = Float.floatToRawIntBits((float) (par1 + this.x_o));
        this.rb[this.rbi + 1] = Float.floatToRawIntBits((float) (par3 + this.y_o));
        this.rb[this.rbi + 2] = Float.floatToRawIntBits((float) (par5 + this.z_o));
        this.rbi += 10;
        this.verts++;
        this.vertices++;
    }

    public void addVertexWithUV (double i, double j, double k, double l, double m) {
        this.setTextureUV(l, m);
        this.addVertex(i, j, k);
    }

    public void addVertexWithUVW (double i, double j, double k, double l, double m, double n) {
        this.setTextureUVW(l, m, n);
        this.addVertex(i, j, k);
    }

    public void setNormal (float x, float y, float z) {
        this.in = true;
        final byte b0 = (byte) (int) (x * 127.0F);
        final byte b1 = (byte) (int) (y * 127.0F);
        final byte b2 = (byte) (int) (z * 127.0F);
        this.n = b0 & 255 | (b1 & 255) << 8 | (b2 & 255) << 16;
    }

    public void setTextureUV (double i, double j) {
        this.ht = true;
        this.u = i;
        this.v = j;
        this.w = 1.0D;
    }

    public void setTextureUVW (double i, double j, double k) {
        this.ht = true;
        this.u = i;
        this.v = j;
        this.w = k;
    }

    public void setTranslation (double x, double y, double z) {
        this.x_o = x;
        this.y_o = y;
        this.z_o = z;
    }

    public void addTranslation (float x, float y, float z) {
        this.x_o += x;
        this.y_o += y;
        this.z_o += z;
    }
}