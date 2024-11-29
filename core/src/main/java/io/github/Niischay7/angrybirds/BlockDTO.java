package io.github.Niischay7.angrybirds;


import java.io.Serializable;

    public class BlockDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String material;
        private float size;
        private int hp;
        private float x;
        private float y;
        private boolean isDestroyed;

        public BlockDTO(blocks block) {
            this.material = block.material;
            this.size = block.size;
            this.hp = block.hp;
            this.x = block.getX();
            this.y = block.getY();
            this.isDestroyed = block.isDestroyed();
        }

        public blocks toBlock() {
            switch (material.toLowerCase()) {
                case "stone":
                    stoneblock block = new stoneblock(size, hp);
                    block.setPosition(x, y);
                    block.isDestroyed = isDestroyed;
                    return block;
                case "wood":
                    woodblock block1 = new woodblock(size, hp);
                    block1.setPosition(x, y);
                    block1.isDestroyed = isDestroyed;
                    return block1;
                case "glass":
                    glassblock block2 = new glassblock(size, hp);
                    block2.setPosition(x, y);
                    block2.isDestroyed = isDestroyed;
                    return block2;
                default:
                    throw new IllegalArgumentException("Unknown block material: " + material);
            }
        }


    }

