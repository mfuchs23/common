package org.dbdoclet.jive.filter;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class BlurFilter {

	public static BufferedImage boxBlur(BufferedImage image, int radius) {

		if (radius < 1) {
			throw new IllegalArgumentException("Radius must be >= 1");
		}

		int size = radius * radius;
		float[] data = new float[size];

		for (int i = 0; i < data.length; i++) {
			data[i] = 1.0f / new Float(data.length);
		}

		Kernel kernel = new Kernel(radius, radius, data);
		BufferedImageOp blurFilter = new ConvolveOp(kernel,
				ConvolveOp.EDGE_NO_OP, null);
		return blurFilter.filter(image, null);
	}

	public static BufferedImage gaussianBlur(BufferedImage image, int radius) {

		int r = (int) Math.ceil(radius);
		int rows = r * 2 + 1;

		float[] matrix = new float[rows];
		float sigma = radius / 3;
		float sigma22 = 2 * sigma * sigma;
		float sigmaPi2 = (float) (2 * Math.PI * sigma);
		float sqrtSigmaPi2 = (float) Math.sqrt(sigmaPi2);
		float radius2 = radius * radius;
		float total = 0;
		int index = 0;

		for (int row = -r; row <= r; row++) {
			float distance = row * row;
			if (distance > radius2)
				matrix[index] = 0;
			else
				matrix[index] = (float) Math.exp(-(distance) / sigma22)
						/ sqrtSigmaPi2;
			total += matrix[index];
			index++;
		}

		for (int i = 0; i < rows; i++) {
			matrix[i] /= total;
		}

		Kernel kernelVertical = new Kernel(rows, 1, matrix);
		Kernel kernelHorizontal = new Kernel(1, rows, matrix);

		BufferedImageOp blurFilter = new ConvolveOp(kernelVertical,
				ConvolveOp.EDGE_NO_OP, null);
		image = blurFilter.filter(image, null);

		blurFilter = new ConvolveOp(kernelHorizontal, ConvolveOp.EDGE_NO_OP,
				null);
		image = blurFilter.filter(image, null);

		return image;
	}

}
