package cope.nebula.util.world.entity.player.rotation;

import cope.nebula.util.Globals;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

/**
 * Calculates rotations
 *
 * @author aesthetical
 * @since 3/10/22
 */
public class AngleUtil implements Globals {
    /**
     * Calculates a rotation to an entity
     * @param entity The entity to rotate to
     * @param bone Where to rotate on the entity
     * @return a rotation object to the entity
     */
    public static Rotation toEntity(Entity entity, Bone bone) {
        // check for null entities
        if (entity == null) {
            return Rotation.INVALID_ROTATION;
        }

        return toVec(getEyes(entity, bone));
    }

    /**
     * Calculates a rotation to a block position
     * @param pos The position
     * @param facing The facing direction
     * @return a rotation object to the block
     */
    public static Rotation toBlock(BlockPos pos, EnumFacing facing) {
        double x = interpolate(mc.player.posX, mc.player.lastTickPosX);
        double y = interpolate(mc.player.posY, mc.player.lastTickPosY);
        double z = interpolate(mc.player.posZ, mc.player.lastTickPosZ);

        Vec3d diff = new Vec3d(
                pos.getX() + 0.5 - x + facing.getXOffset() / 2.0,
                pos.getY() + 0.5,
                pos.getZ() + 0.5 - z + facing.getZOffset() / 2.0
        );

        double distance = MathHelper.sqrt(diff.x * diff.x + diff.z * diff.z);

        float yaw = (float) (Math.atan2(diff.z, diff.x) * 180.0 / Math.PI - 90.0);
        float pitch = (float) (Math.atan2(y + mc.player.getEyeHeight() - diff.y, distance) * 180.0 / Math.PI);

        if (yaw < 0.0f) {
            yaw += 360.0f;
        }

        return new Rotation(yaw, pitch);
    }

    /**
     * Calculates a rotation from a vector
     * @param vec The vector
     * @return a rotation to this vector
     */
    public static Rotation toVec(Vec3d vec) {
        Vec3d diff = diff(vec, getEyes(mc.player, Bone.HEAD));

        double distance = MathHelper.sqrt(diff.x * diff.x + diff.z * diff.z);

        float yaw = (float) (Math.atan2(diff.z, diff.x) * 180.0 / Math.PI - 90.0);
        float pitch = (float) (Math.atan2(diff.y * -1.0, distance) * 180.0 / Math.PI);

        if (yaw < 0.0f) {
            yaw += 360.0f;
        }

        return new Rotation(yaw, pitch);
    }

    /**
     * Finds the difference in two Vec3d's
     * @param from The first vector
     * @param to The second vector
     * @return a vector with the differences
     */
    public static Vec3d diff(Vec3d from, Vec3d to) {
        return new Vec3d(from.x - to.x, from.y - to.y, from.z - to.z);
    }

    /**
     * Gets an entities eye position
     * @param entity The entity to get the eye position of
     * @param bone Where to rotate on the entity
     * @return a vector of the eye position of this entity
     */
    public static Vec3d getEyes(Entity entity, Bone bone) {
        double x = interpolate(entity.posX, entity.prevPosX);
        double y = interpolate(entity.posY, entity.prevPosY);
        double z = interpolate(entity.posZ, entity.prevPosZ);

        switch (bone) {
            case HEAD: {
                y += entity.getEyeHeight();
                break;
            }

            case CHEST: {
                y += (entity.getEyeHeight() / 2.0) + 0.35;
                break;
            }

            case LEGS: {
                y += (entity.getEyeHeight() / 2.0);
                break;
            }

            case FEET: {
                y += 0.05;
                break;
            }
        }

        return new Vec3d(x, y, z);
    }

    /**
     * Interpolates two points
     * @param start The current (start) value
     * @param end The previous (end) value
     * @return an interpolated value between start and end * renderPartialTicks
     */
    public static double interpolate(double start, double end) {
        float partialTicks = mc.getRenderPartialTicks();
        if (partialTicks == 1.0f) {
            return start;
        }

        return end + (start - end) * partialTicks;
    }
}
