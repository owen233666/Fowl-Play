package aqario.fowlplay.common.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public record CuboidRadius<T extends Number & Comparable<? super T>>(T xz, T y) {
    public Vec3i toVec3i() {
        return new Vec3i(this.xz.intValue(), this.y.intValue(), this.xz.intValue());
    }

    public BlockPos toBlockPos() {
        return BlockPos.ofFloored(this.xz.intValue(), this.y.intValue(), this.xz.intValue());
    }

    public Vec3d toVec3d() {
        return new Vec3d(this.xz.doubleValue(), this.y.doubleValue(), this.xz.doubleValue());
    }

    public Box inflateBox(Box box) {
        return box.expand(this.xz.doubleValue(), this.y.doubleValue(), this.xz.doubleValue());
    }
}
