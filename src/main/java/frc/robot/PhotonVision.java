package frc.robot;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;

import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.photonvision.*;

public class PhotonVision {

    public AprilTagFieldLayout m_aprilTagFieldLayout;
    public PhotonCamera m_cam;
    public PhotonPoseEstimator m_photonPoseEstimator;
    
    public PhotonVision()
    {
        try {
            m_aprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2024Crescendo.m_resourceFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
             //Forward Camera, change name
        m_cam = new PhotonCamera("Camera_Module_v1");
        Transform3d robotToCam = new Transform3d(new Translation3d(0.5, 0.0, 0.5), new Rotation3d(0,0,0)); //Cam mounted facing forward, half a meter forward of center, half a meter up from center.

        // Construct PhotonPoseEstimator
         m_photonPoseEstimator = new PhotonPoseEstimator(m_aprilTagFieldLayout, PoseStrategy.CLOSEST_TO_REFERENCE_POSE, m_cam, robotToCam);
    }

    public Transform2d GetCamData()
    {
        Transform2d retval = new Transform2d();

        // Query the latest result from PhotonVision
        var result = m_cam.getLatestResult();

        // Check if the latest result has any targets.
        boolean hasTargets = result.hasTargets();

        if(hasTargets)
        {
            // Get the current best target.
            PhotonTrackedTarget target = result.getBestTarget();
            // Get information from target.
            double yaw = target.getYaw();
            double pitch = target.getPitch();
            double area = target.getArea();
            double skew = target.getSkew();
            //Transform2d pose = target.getCameraToTarget();
            //List<TargetCorner> corners = target.getCorners();
            // Get information from target.
            int targetID = target.getFiducialId();
            double poseAmbiguity = target.getPoseAmbiguity();
            Transform3d bestCameraToTarget = target.getBestCameraToTarget();
            Transform3d alternateCameraToTarget = target.getAlternateCameraToTarget();
            if(targetID > 0)
            {
                //Do something!
                retval = new Transform2d(bestCameraToTarget.getX(),bestCameraToTarget.getY(),Rotation2d.fromRadians(yaw));
            }
        }
        return retval;
    }

    public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        m_photonPoseEstimator.setReferencePose(prevEstimatedRobotPose);
        return m_photonPoseEstimator.update();
    }
}
