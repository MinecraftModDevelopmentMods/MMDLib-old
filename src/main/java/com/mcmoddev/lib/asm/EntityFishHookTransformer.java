package com.mcmoddev.lib.asm;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

class EntityFishHookTransformer implements ITransformer {

    @Override
    public String getTarget () {

        return "net.minecraft.entity.projectile.EntityFishHook";
    }

    @Override
    public void transform (ClassNode node, boolean dev) {

        for (final MethodNode methodNode : node.methods) {
            if (methodNode.name.equals(dev ? "onUpdate" : "func_70088_a") && methodNode.desc.equals("()V")) {
                InsnList needle = new InsnList();
                needle.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "getItem", "()Lnet/minecraft/item/Item;", false));

                AbstractInsnNode insertNode = ASMUtils.findFirstNodeFromNeedle(methodNode.instructions, needle);
                needle.clear();

                needle.add(new FieldInsnNode(GETSTATIC, "net/minecraft/init/Items", "FISHING_ROD", "Lnet/minecraft/item/ItemFishingRod;"));
                needle.add(new JumpInsnNode(IF_ACMPNE, new LabelNode()));
                AbstractInsnNode labelNode = ASMUtils.findLastNodeFromNeedle(methodNode.instructions, needle);

                ASMUtils.removeNeedleFromHaystack(methodNode.instructions, needle);

                InsnList newInst = new InsnList();
                newInst.add(new TypeInsnNode(INSTANCEOF, "net/minecraft/item/ItemFishingRod"));
                newInst.add(new JumpInsnNode(IFNE, (LabelNode) labelNode));
                methodNode.instructions.insert(insertNode);
            }
        }
    }
}