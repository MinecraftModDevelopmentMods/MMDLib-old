package com.mcmoddev.lib.asm;

import org.objectweb.asm.tree.*;

class EntityFishHookTransformer implements ITransformer {

    @Override
    public String getTarget() {

        return "net.minecraft.entity.projectile.EntityFishHook";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {

        for (final MethodNode methodNode : node.methods) {
            if (methodNode.name.equals(dev ? "onUpdate" : "func_70088_a") && methodNode.desc.equals("()V")) {

                InsnList labelNeedle = new InsnList();
                labelNeedle.add(new VarInsnNode(ALOAD, 0));
                labelNeedle.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/projectile/EntityFishHook","angler","Lnet/minecraft/entity/player/EntityPlayer;"));
                labelNeedle.add(new InsnNode(ACONST_NULL));
                labelNeedle.add(new FieldInsnNode(PUTFIELD, "net/minecraft/entity/player/EntityPlayer","fishEntity","Lnet/minecraft/entity/projectile/EntityFishHook;"));
                labelNeedle.add(new LabelNode());
                labelNeedle.add(new LineNumberNode(-1, new LabelNode()));
                labelNeedle.add(new InsnNode(RETURN));
                labelNeedle.add(new LabelNode());
                AbstractInsnNode labelNode = ASMUtils.findLastNodeFromNeedle(methodNode.instructions, labelNeedle);

                InsnList needle = new InsnList();
                needle.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/entity/player/EntityPlayer", "getHeldItemMainhand", "()Lnet/minecraft/item/ItemStack;", false));
                needle.add(new VarInsnNode(ASTORE, 1));
                needle.add(new LabelNode());
                needle.add(new LineNumberNode(-1, new LabelNode()));

                InsnList newInst = new InsnList();
                newInst.add(new VarInsnNode(ALOAD, 0));
                newInst.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/projectile/EntityFishHook", "angler", "Lnet/minecraft/entity/player/EntityPlayer;"));
                newInst.add(new VarInsnNode(ALOAD, 1));
                newInst.add(new VarInsnNode(ALOAD, 0));
                newInst.add(new VarInsnNode(ALOAD, 0));
                newInst.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/projectile/EntityFishHook", "angler", "Lnet/minecraft/entity/player/EntityPlayer;"));
                newInst.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/entity/projectile/EntityFishHook", "getDistanceSqToEntity", "(Lnet/minecraft/entity/Entity;)D", false));
                newInst.add(new MethodInsnNode(INVOKESTATIC, "com/mcmoddev/lib/asm/ASMHooks", "isFishingRod", "(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;D)Z", false));
                newInst.add(new JumpInsnNode(IFNE, (LabelNode) labelNode));
                newInst.add(new LabelNode());
                AbstractInsnNode insertNode = ASMUtils.findLastNodeFromNeedle(methodNode.instructions, needle);

                methodNode.instructions.insert(insertNode, newInst);
            }
        }
    }
}