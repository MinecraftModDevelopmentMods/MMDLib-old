package com.mcmoddev.lib.client.model;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ModelPoolEntry {

    public String name;
    public PositionTransformVertex[] vertices;
    public TexturedPolygon[] faces;
    public Map<String, TransformGroupBone> groups;
    public Map<String, TextureGroup> textures;
    protected TransformGroupBone group;
    protected TextureGroup texture;
    protected String[] fileExtensions;

    public File checkValidPath (String path) {
        File file = null;
        for (int index = 0; index < this.fileExtensions.length && (file == null || !file.exists()); index++) {
            String absPath = path;
            if (!path.endsWith("." + this.fileExtensions[index]))
                absPath += "." + this.fileExtensions[index];
            file = new File(absPath);
        }
        if (file == null || !file.exists())
            return null;
        return file;
    }

    public abstract void getModel (File file);

    /**
     * Sets the current transformation group. The transformation group is used to allow for
     * vertex transformation. If a transformation group does not exist, a new one will be
     * created.
     *
     * @param groupName the name of the transformation group you want to switch to
     */
    protected void setGroup (String groupName) {
        this.setGroup(groupName, new Bone(0, 0, 0, 0), 1D);
    }

    /**
     * Sets the current transformation group. The transformation group is used to allow for
     * vertex transformation. If a transformation group does not exist, a new one will be
     * created.
     *
     * @param groupName the name of the transformation group you want to switch to
     * @param bone the Bone this transformation group is attached to
     * @param weight the weight of the transformation group
     */
    protected void setGroup (String groupName, Bone bone, double weight) {
        if (this.groups.size() == 0 || !this.groups.containsKey(groupName))
            this.groups.put(groupName, new TransformGroupBone(bone, weight));
        this.group = this.groups.get(groupName);
    }

    /**
     * Sets the current texture group, which is used to switch the textures on a per-model
     * base. Do note that any model that is rendered afterwards will use the same texture. To
     * counter it, set a default texture, either at initialization or before rendering.
     *
     * @param groupName The name of the texture group. If the texture group doesn't exist, it
     *        creates a new group automatically.
     */
    protected void setTextureGroup (String groupName) {
        if (this.textures.size() == 0 || !this.textures.containsKey(groupName))
            this.textures.put(groupName, new TextureGroup());
        this.texture = this.textures.get(groupName);
    }

    protected void applyGroups (Map<String, TransformGroup> groupsMap, Map<String, TextureGroup> texturesMap) {
        final Set<String> groupsCol = this.groups.keySet();
        final Collection<String> texturesCol = this.textures.keySet();
        final Iterator<String> groupsItr = groupsCol.iterator();
        final Iterator<String> texturesItr = texturesCol.iterator();
        while (groupsItr.hasNext()) {
            int nameIdx = 0;
            final String groupKey = groupsItr.next();
            String currentGroup = this.name + "_" + nameIdx + ":" + groupKey;
            while (groupsMap.size() > 0 && groupsMap.containsKey(currentGroup)) {
                nameIdx++;
                currentGroup = this.name + "_" + nameIdx + ":" + groupKey;
            }
            groupsMap.put(currentGroup, this.groups.get(groupKey));
        }
        while (texturesItr.hasNext()) {
            int nameIdx = 0;
            final String groupKey = texturesItr.next();
            String currentGroup = this.name + "_" + nameIdx + ":" + groupKey;
            while (groupsMap.size() > 0 && texturesMap.containsKey(currentGroup)) {
                nameIdx++;
                currentGroup = this.name + "_" + nameIdx + ":" + groupKey;
            }
            texturesMap.put(currentGroup, this.textures.get(groupKey));
        }
    }
}
