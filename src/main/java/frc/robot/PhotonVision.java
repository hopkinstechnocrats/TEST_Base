package frc.robot;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.net.PortForwarder;

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
    public PhotonCamera n_cam;
    public PhotonPoseEstimator m_photonPoseEstimator;
    
    public PhotonVision()
    {
        try {
            m_aprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2024Crescendo.m_resourceFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
             //Forward Camera, change name
        //m_cam = new PhotonCamera("Camera_Module_v3");
        n_cam = new PhotonCamera("Camera2");
        Transform3d robotToCam = new Transform3d(new Translation3d(0.5, 0.0, 0.5), new Rotation3d(0,0,0)); //Cam mounted facing forward, half a meter forward of center, half a meter up from center.

        // Construct PhotonPoseEstimator
         m_photonPoseEstimator = new PhotonPoseEstimator(m_aprilTagFieldLayout, PoseStrategy.CLOSEST_TO_REFERENCE_POSE, m_cam, robotToCam);

         PortForwarder.add(5800, "Camera_Module_v3", 5800);
         PortForwarder.add(5800, "Camera2", 5800);
    }

    public Transform3d GetCamData()
    {
        Transform3d retval = new Transform3d();

        // Query the latest result from PhotonVision
        var result = n_cam.getLatestResult();

        // Check if the latest result has any targets.
        boolean hasTargets = result.hasTargets();

        if(hasTargets)
        {
            PhotonTrackedTarget target = result.getBestTarget();
            double yaw = target.getYaw();
            double area = target.getArea();
            double pitch = target.getPitch(); 
            double skew = target.getSkew();
            //Transform2d pose = target.getCameraToTarget();
            //List<TargetCorner> corners = target.getCorners();
            // Get information from target.
            int targetID = target.getFiducialId();
            Transform3d bestCameraToTarget = target.getBestCameraToTarget();
            Rotation3d rotator = new Rotation3d(targetID, pitch, yaw);
            //System.out.println(bestCameraToTarget.getZ());
            System.out.println(targetID);
            if(targetID > 0)
            {
                //Do something!
                //need to retun a "z" value
                retval = new Transform3d(bestCameraToTarget.getX(), bestCameraToTarget.getY(), bestCameraToTarget.getZ(), rotator);
            } else {
                System.out.println("no april tag seen");
            }
        }
        return retval;
    }
    


    public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        m_photonPoseEstimator.setReferencePose(prevEstimatedRobotPose);
        return m_photonPoseEstimator.update();
    }
}
