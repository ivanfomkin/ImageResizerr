import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class ImageResizer implements Runnable{

    private List<File> files;
    private int newWidth;
    private String destFolder;
    private long startTime;

    public ImageResizer(List<File> files, int newWidth, String destFolder, long startTime) {
        this.files = files;
        this.newWidth = newWidth;
        this.destFolder = destFolder;
        this.startTime = startTime;
    }

    @Override
    public void run() {
        try {
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    continue;
                }

                int newHeight = (int) Math.round(image.getHeight() / (image.getWidth() / (double) newWidth)
                );

                BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

                int withStep = image.getWidth() / newWidth;
                int heightStep = image.getHeight() / newHeight;

                for (int x = 0; x < newWidth; x++) {
                    for (int y = 0; y < newHeight; y++) {
                        int rgb = image.getRGB(x * withStep, y * heightStep);
                        newImage.setRGB(x,y,rgb);
                    }
                }
                File newFile = new File(destFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Finished after start: " + (System.currentTimeMillis() - startTime));
    }
}
