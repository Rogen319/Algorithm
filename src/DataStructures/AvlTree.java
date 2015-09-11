    package DataStructures;

    // BinarySearchTree class
    //
    // CONSTRUCTION: with no initializer
    //
    // ******************PUBLIC OPERATIONS*********************
    // void insert( x )       --> Insert x
    // void remove( x )       --> Remove x (unimplemented)
    // Comparable find( x )   --> Return item that matches x
    // Comparable findMin( )  --> Return smallest item
    // Comparable findMax( )  --> Return largest item
    // boolean isEmpty( )     --> Return true if empty; else false
    // void makeEmpty( )      --> Remove all items
    // void printTree( )      --> Print tree in sorted order

    /**
     * Implements an AVL tree.
     * Note that all "matching" is based on the compareTo method.
     * @author Mark Allen Weiss
     */
    public class AvlTree
    {
        /**
         * Construct the tree.
         */
        public AvlTree( )
        {
            root = null;
        }

        /**
         * Insert into the tree; duplicates are ignored.
         * @param x the item to insert.
         */
        public void insert( Comparable x )
        {
            root = insert( x, root );
        }

        /**
         * Remove from the tree. Nothing is done if x is not found.
         * @param x the item to remove.
         */
        public void remove( Comparable x )
        {
        	remove( x, root );
        }
        public AvlNode remove( Comparable x, AvlNode t )
        {
        	if(t == null)
    			return null;
    		if(find(x)==null) return t;
    		int compareResult = x.compareTo(t.element);
    		
    		if(compareResult < 0){
    			t.left = remove(x, t.left);
    			//完了之后验证该子树是否平衡
    			if(t.right != null){		//若右子树为空，则一定是平衡的，此时左子树相当对父节点深度最多为1, 所以只考虑右子树非空情况
    				if(t.left == null){		//若左子树删除后为空，则需要判断右子树
    					if(height(t.right)-t.height == 2){
    						AvlNode k = t.right;
    						if(k.right != null){		//右子树存在，按正常情况单旋转
    							t = rotateWithRightChild(t);
    						}else{						//否则是右左情况，双旋转				
    							t = doubleWithRightChild(t);
    						}
    					}
    				}else{					//否则判断左右子树的高度差
    					//左子树自身也可能不平衡，故先平衡左子树，再考虑整体
    					AvlNode k = t.left;
    					//删除操作默认用右子树上最小节点补删除的节点
    					//k的左子树高度不低于k的右子树
    					if(k.right != null){
    						if(height(k.left)-height(k.right) == 2){
    							AvlNode m = k.left;
    							if(m.left != null){		//左子树存在，按正常情况单旋转
    								k = rotateWithLeftChild(k);
    							}else{						//否则是左右情况，双旋转				
    								k = doubleWithLeftChild(k);								
    							}
    						}
    					}else{
    						if(height(k.left) - k.height ==2){
    							AvlNode m = k.left;
    							if(m.left != null){		//左子树存在，按正常情况单旋转
    								k = rotateWithLeftChild(k);
    							}else{						//否则是左右情况，双旋转				
    								k = doubleWithLeftChild(k);
    							}
    						}
    					}					
    					if(height(t.right)-height(t.left) == 2){
    						//右子树自身一定是平衡的，左右失衡的话单旋转可以解决问题
    						t = rotateWithRightChild(t);
    					}
    				}
    			}
    			//完了之后更新height值
    			t.height = Math.max(height(t.left), height(t.right))+1;
    		}else if(compareResult > 0){
    			t.right = remove(x, t.right);
    			//下面验证子树是否平衡
    			if(t.left != null){			//若左子树为空，则一定是平衡的，此时右子树相当对父节点深度最多为1
    				if(t.right == null){		//若右子树删除后为空，则只需判断左子树
    					if(height(t.left)-t.height ==2){
    						AvlNode k = t.left;
    						if(k.left != null){
    							t = rotateWithLeftChild(t);
    						}else{
    							t = doubleWithLeftChild(t);
    						}
    					}					
    				}else{				//若右子树删除后非空，则判断左右子树的高度差
    					//右子树自身也可能不平衡，故先平衡右子树，再考虑整体
    					AvlNode k = t.right;
    					//删除操作默认用右子树上最小节点（靠左）补删除的节点
    					//k的右子树高度不低于k的左子树					
    					if(k.left != null){
    						if(height(k.right)-height(k.left) == 2){
    							AvlNode m = k.right;
    							if(m.right != null){		//右子树存在，按正常情况单旋转
    								k = rotateWithRightChild(k);
    							}else{						//否则是右左情况，双旋转				
    								k = doubleWithRightChild(k);
    							}
    						}
    					}else{
    						if(height(k.right)-k.height == 2){
    							AvlNode m = k.right;
    							if(m.right != null){		//右子树存在，按正常情况单旋转
    								k = rotateWithRightChild(k);
    							}else{						//否则是右左情况，双旋转				
    								k = doubleWithRightChild(k);
    							}
    						}
    					}					
    					if(height(t.left) - height(t.right) == 2){
    						//左子树自身一定是平衡的，左右失衡的话单旋转可以解决问题
    						t = rotateWithLeftChild(t);			
    					}
    				}
    			}
    			//完了之后更新height值
    			t.height = Math.max(height(t.left), height(t.right))+1;
    		}else if(t.left != null && t.right != null){
    			//默认用其右子树的最小数据代替该节点的数据并递归的删除那个节点
    			t.element = findMin(t.right).element;
    			t.right = remove(t.element, t.right);			
    			if(t.right == null){		//若右子树删除后为空，则只需判断左子树与根的高度差
    				if(height(t.left)-t.height ==2){
    					AvlNode k = t.left;
    					if(k.left != null){
    						t = rotateWithLeftChild(t);
    					}else{
    						t = doubleWithLeftChild(t);
    					}
    				}					
    			}else{				//若右子树删除后非空，则判断左右子树的高度差
    				//右子树自身也可能不平衡，故先平衡右子树，再考虑整体
    				AvlNode k = t.right;
    				//删除操作默认用右子树上最小节点（靠左）补删除的节点
    				
    				if(k.left != null){
    					if(height(k.right)-height(k.left) == 2){
    						AvlNode m = k.right;
    						if(m.right != null){		//右子树存在，按正常情况单旋转
    							k = rotateWithRightChild(k);
    						}else{						//否则是右左情况，双旋转				
    							k = doubleWithRightChild(k);
    						}
    					}	
    				}else{
    					if(height(k.right)-k.height == 2){
    						AvlNode m = k.right;
    						if(m.right != null){		//右子树存在，按正常情况单旋转
    							k = rotateWithRightChild(k);
    						}else{						//否则是右左情况，双旋转				
    							k = doubleWithRightChild(k);
    						}
    					}	
    				}
    				//左子树自身一定是平衡的，左右失衡的话单旋转可以解决问题
    				if(height(t.left) - height(t.right) == 2){
    					t = rotateWithLeftChild(t);			
    				}
    			}
    			//完了之后更新height值
    			t.height = Math.max(height(t.left), height(t.right))+1;
    		}else{
    			t = (t.left != null)?t.left:t.right;		
    		}
    		return t;
        }

        /**
         * Find the smallest item in the tree.
         * @return smallest item or null if empty.
         */
        public Comparable findMin( )
        {
            return elementAt( findMin( root ) );
        }

        /**
         * Find the largest item in the tree.
         * @return the largest item of null if empty.
         */
        public Comparable findMax( )
        {
            return elementAt( findMax( root ) );
        }

        /**
         * Find an item in the tree.
         * @param x the item to search for.
         * @return the matching item or null if not found.
         */
        public Comparable find( Comparable x )
        {
            return elementAt( find( x, root ) );
        }

        /**
         * Make the tree logically empty.
         */
        public void makeEmpty( )
        {
            root = null;
        }

        /**
         * Test if the tree is logically empty.
         * @return true if empty, false otherwise.
         */
        public boolean isEmpty( )
        {
            return root == null;
        }

        /**
         * Print the tree contents in sorted order.
         */
        public void printTree( )
        {
            if( isEmpty( ) )
                System.out.println( "Empty tree" );
            else
                printTree( root );
        }

        /**
         * Internal method to get element field.
         * @param t the node.
         * @return the element field or null if t is null.
         */
        private Comparable elementAt( AvlNode t )
        {
            return t == null ? null : t.element;
        }

        /**
         * Internal method to insert into a subtree.
         * @param x the item to insert.
         * @param t the node that roots the tree.
         * @return the new root.
         */
        private AvlNode insert( Comparable x, AvlNode t )
        {
            if( t == null )
                t = new AvlNode( x, null, null );
            else if( x.compareTo( t.element ) < 0 )
            {
                t.left = insert( x, t.left );
                if( height( t.left ) - height( t.right ) == 2 )
                    if( x.compareTo( t.left.element ) < 0 )
                        t = rotateWithLeftChild( t );
                    else
                        t = doubleWithLeftChild( t );
            }
            else if( x.compareTo( t.element ) > 0 )
            {
                t.right = insert( x, t.right );
                if( height( t.right ) - height( t.left ) == 2 )
                    if( x.compareTo( t.right.element ) > 0 )
                        t = rotateWithRightChild( t );
                    else
                        t = doubleWithRightChild( t );
            }
            else
                ;  // Duplicate; do nothing
            t.height = max( height( t.left ), height( t.right ) ) + 1;
            return t;
        }

        /**
         * Internal method to find the smallest item in a subtree.
         * @param t the node that roots the tree.
         * @return node containing the smallest item.
         */
        private AvlNode findMin( AvlNode t )
        {
            if( t == null )
                return t;

            while( t.left != null )
                t = t.left;
            return t;
        }

        /**
         * Internal method to find the largest item in a subtree.
         * @param t the node that roots the tree.
         * @return node containing the largest item.
         */
        private AvlNode findMax( AvlNode t )
        {
            if( t == null )
                return t;

            while( t.right != null )
                t = t.right;
            return t;
        }

        /**
         * Internal method to find an item in a subtree.
         * @param x is item to search for.
         * @param t the node that roots the tree.
         * @return node containing the matched item.
         */
        private AvlNode find( Comparable x, AvlNode t )
        {
            while( t != null )
                if( x.compareTo( t.element ) < 0 )
                    t = t.left;
                else if( x.compareTo( t.element ) > 0 )
                    t = t.right;
                else
                    return t;    // Match

            return null;   // No match
        }

        /**
         * Internal method to print a subtree in sorted order.
         * @param t the node that roots the tree.
         */
        private void printTree( AvlNode t )
        {
            if( t != null )
            {
                printTree( t.left );
                System.out.println( t.element );
                printTree( t.right );
            }
        }

        /**
         * Return the height of node t, or -1, if null.
         */
        private static int height( AvlNode t )
        {
            return t == null ? -1 : t.height;
        }

        /**
         * Return maximum of lhs and rhs.
         */
        private static int max( int lhs, int rhs )
        {
            return lhs > rhs ? lhs : rhs;
        }

        /**
         * Rotate binary tree node with left child.
         * For AVL trees, this is a single rotation for case 1.
         * Update heights, then return new root.
         */
        private static AvlNode rotateWithLeftChild( AvlNode k2 )
        {
            AvlNode k1 = k2.left;
            k2.left = k1.right;
            k1.right = k2;
            k2.height = max( height( k2.left ), height( k2.right ) ) + 1;
            k1.height = max( height( k1.left ), k2.height ) + 1;
            return k1;
        }

        /**
         * Rotate binary tree node with right child.
         * For AVL trees, this is a single rotation for case 4.
         * Update heights, then return new root.
         */
        private static AvlNode rotateWithRightChild( AvlNode k1 )
        {
            AvlNode k2 = k1.right;
            k1.right = k2.left;
            k2.left = k1;
            k1.height = max( height( k1.left ), height( k1.right ) ) + 1;
            k2.height = max( height( k2.right ), k1.height ) + 1;
            return k2;
        }

        /**
         * Double rotate binary tree node: first left child
         * with its right child; then node k3 with new left child.
         * For AVL trees, this is a double rotation for case 2.
         * Update heights, then return new root.
         */
        private static AvlNode doubleWithLeftChild( AvlNode k3 )
        {
            k3.left = rotateWithRightChild( k3.left );
            return rotateWithLeftChild( k3 );
        }

        /**
         * Double rotate binary tree node: first right child
         * with its left child; then node k1 with new right child.
         * For AVL trees, this is a double rotation for case 3.
         * Update heights, then return new root.
         */
        private static AvlNode doubleWithRightChild( AvlNode k1 )
        {
            k1.right = rotateWithLeftChild( k1.right );
            return rotateWithRightChild( k1 );
        }

          /** The tree root. */
        private AvlNode root;


            // Test program
        public static void main( String [ ] args )
        {
            AvlTree t = new AvlTree( );
            final int NUMS = 10;
            final int GAP  =   37;

            System.out.println( "Checking... (no more output means success)" );

            for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
                t.insert( new MyInteger( i ) );
            
            t.printTree( );
            t.remove(new MyInteger(6));
            t.printTree( );
//            if( ((MyInteger)(t.findMin( ))).intValue( ) != 1 ||
//                ((MyInteger)(t.findMax( ))).intValue( ) != NUMS - 1 )
//                System.out.println( "FindMin or FindMax error!" );
//
//            for( int i = 1; i < NUMS; i++ )
//                 if( ((MyInteger)(t.find( new MyInteger( i ) ))).intValue( ) != i )
//                     System.out.println( "Find error1!" );
    }
}
