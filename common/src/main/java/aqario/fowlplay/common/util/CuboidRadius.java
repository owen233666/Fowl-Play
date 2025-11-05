package aqario.fowlplay.common.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public record CuboidRadius<T extends Number & Comparable<? super T>>(T horizontal, T vertical) {
    public Vec3i toVec3i() {
        return new Vec3i(this.horizontal.intValue(), this.vertical.intValue(), this.horizontal.intValue());
    }

    public BlockPos toBlockPos() {
        return BlockPos.ofFloored(this.horizontal.intValue(), this.vertical.intValue(), this.horizontal.intValue());
    }

    public Vec3d toVec3d() {
        return new Vec3d(this.horizontal.doubleValue(), this.vertical.doubleValue(), this.horizontal.doubleValue());
    }

    public Box inflateBox(Box box) {
        return box.expand(this.horizontal.doubleValue(), this.vertical.doubleValue(), this.horizontal.doubleValue());
    }
}
