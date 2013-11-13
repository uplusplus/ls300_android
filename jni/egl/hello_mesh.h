#ifndef _HELLO_MESH_
#define _HELLO_MESH_

#define Float2Fixed(var) (GLfixed)(var * 10 * 0x100000)

GLfixed helloVertex[] = {	// HELLO EGL
	// H
	Float2Fixed(0.0f), Float2Fixed(0.0f),  Float2Fixed(0.1f), Float2Fixed(0.0f),  Float2Fixed(0.1f), Float2Fixed(0.7f),
	Float2Fixed(0.0f), Float2Fixed(0.0f),  Float2Fixed(0.1f), Float2Fixed(0.7f),  Float2Fixed(0.0f), Float2Fixed(0.7f),

	Float2Fixed(0.1f), Float2Fixed(0.3f),  Float2Fixed(0.3f), Float2Fixed(0.3f),  Float2Fixed(0.3f), Float2Fixed(0.4f),
	Float2Fixed(0.1f), Float2Fixed(0.3f),  Float2Fixed(0.3f), Float2Fixed(0.4f),  Float2Fixed(0.1f), Float2Fixed(0.4f),

	Float2Fixed(0.3f), Float2Fixed(0.0f),  Float2Fixed(0.4f), Float2Fixed(0.0f),  Float2Fixed(0.4f), Float2Fixed(0.7f),
	Float2Fixed(0.3f), Float2Fixed(0.0f),  Float2Fixed(0.4f), Float2Fixed(0.7f),  Float2Fixed(0.3f), Float2Fixed(0.7f),

	// E
	Float2Fixed(0.5f), Float2Fixed(0.0f),  Float2Fixed(0.6f), Float2Fixed(0.0f),  Float2Fixed(0.6f), Float2Fixed(0.7f),
	Float2Fixed(0.5f), Float2Fixed(0.0f),  Float2Fixed(0.6f), Float2Fixed(0.7f),  Float2Fixed(0.5f), Float2Fixed(0.7f),

	Float2Fixed(0.6f), Float2Fixed(0.0f),  Float2Fixed(0.9f), Float2Fixed(0.0f),  Float2Fixed(0.9f), Float2Fixed(0.1f),
	Float2Fixed(0.6f), Float2Fixed(0.0f),  Float2Fixed(0.9f), Float2Fixed(0.1f),  Float2Fixed(0.6f), Float2Fixed(0.1f),

	Float2Fixed(0.6f), Float2Fixed(0.3f),  Float2Fixed(0.9f), Float2Fixed(0.3f),  Float2Fixed(0.9f), Float2Fixed(0.4f),
	Float2Fixed(0.6f), Float2Fixed(0.3f),  Float2Fixed(0.9f), Float2Fixed(0.4f),  Float2Fixed(0.6f), Float2Fixed(0.4f),

	Float2Fixed(0.6f), Float2Fixed(0.6f),  Float2Fixed(0.9f), Float2Fixed(0.6f),  Float2Fixed(0.9f), Float2Fixed(0.7f),
	Float2Fixed(0.6f), Float2Fixed(0.6f),  Float2Fixed(0.9f), Float2Fixed(0.7f),  Float2Fixed(0.6f), Float2Fixed(0.7f),

	// L
	Float2Fixed(1.0f), Float2Fixed(0.0f),  Float2Fixed(1.1f), Float2Fixed(0.0f),  Float2Fixed(1.1f), Float2Fixed(0.7f),
	Float2Fixed(1.0f), Float2Fixed(0.0f),  Float2Fixed(1.1f), Float2Fixed(0.7f),  Float2Fixed(1.0f), Float2Fixed(0.7f),

	Float2Fixed(1.1f), Float2Fixed(0.0f),  Float2Fixed(1.4f), Float2Fixed(0.0f),  Float2Fixed(1.4f), Float2Fixed(0.1f),
	Float2Fixed(1.1f), Float2Fixed(0.0f),  Float2Fixed(1.4f), Float2Fixed(0.1f),  Float2Fixed(1.1f), Float2Fixed(0.1f),

	// L
	Float2Fixed(1.5f), Float2Fixed(0.0f),  Float2Fixed(1.6f), Float2Fixed(0.0f),  Float2Fixed(1.6f), Float2Fixed(0.7f),
	Float2Fixed(1.5f), Float2Fixed(0.0f),  Float2Fixed(1.6f), Float2Fixed(0.7f),  Float2Fixed(1.5f), Float2Fixed(0.7f),

	Float2Fixed(1.6f), Float2Fixed(0.0f),  Float2Fixed(1.9f), Float2Fixed(0.0f),  Float2Fixed(1.9f), Float2Fixed(0.1f),
	Float2Fixed(1.6f), Float2Fixed(0.0f),  Float2Fixed(1.9f), Float2Fixed(0.1f),  Float2Fixed(1.6f), Float2Fixed(0.1f),

	// O
	Float2Fixed(2.0f), Float2Fixed(0.0f),  Float2Fixed(2.1f), Float2Fixed(0.0f),  Float2Fixed(2.1f), Float2Fixed(0.7f),
	Float2Fixed(2.0f), Float2Fixed(0.0f),  Float2Fixed(2.1f), Float2Fixed(0.7f),  Float2Fixed(2.0f), Float2Fixed(0.7f),

	Float2Fixed(2.1f), Float2Fixed(0.0f),  Float2Fixed(2.3f), Float2Fixed(0.0f),  Float2Fixed(2.3f), Float2Fixed(0.1f),
	Float2Fixed(2.1f), Float2Fixed(0.0f),  Float2Fixed(2.3f), Float2Fixed(0.1f),  Float2Fixed(2.1f), Float2Fixed(0.1f),

	Float2Fixed(2.1f), Float2Fixed(0.6f),  Float2Fixed(2.3f), Float2Fixed(0.6f),  Float2Fixed(2.3f), Float2Fixed(0.7f),
	Float2Fixed(2.1f), Float2Fixed(0.6f),  Float2Fixed(2.3f), Float2Fixed(0.7f),  Float2Fixed(2.1f), Float2Fixed(0.7f),

	Float2Fixed(2.3f), Float2Fixed(0.0f),  Float2Fixed(2.4f), Float2Fixed(0.0f),  Float2Fixed(2.4f), Float2Fixed(0.7f),
	Float2Fixed(2.3f), Float2Fixed(0.0f),  Float2Fixed(2.4f), Float2Fixed(0.7f),  Float2Fixed(2.3f), Float2Fixed(0.7f),

	// E
	Float2Fixed(2.5f), Float2Fixed(0.0f),  Float2Fixed(2.6f), Float2Fixed(0.0f),  Float2Fixed(2.6f), Float2Fixed(0.7f),
	Float2Fixed(2.5f), Float2Fixed(0.0f),  Float2Fixed(2.6f), Float2Fixed(0.7f),  Float2Fixed(2.5f), Float2Fixed(0.7f),

	Float2Fixed(2.6f), Float2Fixed(0.0f),  Float2Fixed(2.9f), Float2Fixed(0.0f),  Float2Fixed(2.9f), Float2Fixed(0.1f),
	Float2Fixed(2.6f), Float2Fixed(0.0f),  Float2Fixed(2.9f), Float2Fixed(0.1f),  Float2Fixed(2.6f), Float2Fixed(0.1f),

	Float2Fixed(2.6f), Float2Fixed(0.3f),  Float2Fixed(2.9f), Float2Fixed(0.3f),  Float2Fixed(2.9f), Float2Fixed(0.4f),
	Float2Fixed(2.6f), Float2Fixed(0.3f),  Float2Fixed(2.9f), Float2Fixed(0.4f),  Float2Fixed(2.6f), Float2Fixed(0.4f),

	Float2Fixed(2.6f), Float2Fixed(0.6f),  Float2Fixed(2.9f), Float2Fixed(0.6f),  Float2Fixed(2.9f), Float2Fixed(0.7f),
	Float2Fixed(2.6f), Float2Fixed(0.6f),  Float2Fixed(2.9f), Float2Fixed(0.7f),  Float2Fixed(2.6f), Float2Fixed(0.7f),

	// G
	Float2Fixed(3.1f), Float2Fixed(0.6f),  Float2Fixed(3.4f), Float2Fixed(0.6f),  Float2Fixed(3.4f), Float2Fixed(0.7f),
	Float2Fixed(3.1f), Float2Fixed(0.6f),  Float2Fixed(3.4f), Float2Fixed(0.7f),  Float2Fixed(3.1f), Float2Fixed(0.7f),

	Float2Fixed(3.0f), Float2Fixed(0.0f),  Float2Fixed(3.1f), Float2Fixed(0.0f),  Float2Fixed(3.1f), Float2Fixed(0.7f),
	Float2Fixed(3.0f), Float2Fixed(0.0f),  Float2Fixed(3.1f), Float2Fixed(0.7f),  Float2Fixed(3.0f), Float2Fixed(0.7f),

	Float2Fixed(3.1f), Float2Fixed(0.0f),  Float2Fixed(3.4f), Float2Fixed(0.0f),  Float2Fixed(3.4f), Float2Fixed(0.1f),
	Float2Fixed(3.1f), Float2Fixed(0.0f),  Float2Fixed(3.4f), Float2Fixed(0.1f),  Float2Fixed(3.1f), Float2Fixed(0.1f),

	Float2Fixed(3.3f), Float2Fixed(0.1f),  Float2Fixed(3.4f), Float2Fixed(0.1f),  Float2Fixed(3.4f), Float2Fixed(0.3f),
	Float2Fixed(3.3f), Float2Fixed(0.1f),  Float2Fixed(3.4f), Float2Fixed(0.3f),  Float2Fixed(3.3f), Float2Fixed(0.3f),

	Float2Fixed(3.25f), Float2Fixed(0.3f),  Float2Fixed(3.45f), Float2Fixed(0.3f),  Float2Fixed(3.45f), Float2Fixed(0.4f),
	Float2Fixed(3.25f), Float2Fixed(0.3f),  Float2Fixed(3.45f), Float2Fixed(0.4f),  Float2Fixed(3.25f), Float2Fixed(0.4f),

	// L
	Float2Fixed(3.5f), Float2Fixed(0.0f),  Float2Fixed(3.6f), Float2Fixed(0.0f),  Float2Fixed(3.6f), Float2Fixed(0.7f),
	Float2Fixed(3.5f), Float2Fixed(0.0f),  Float2Fixed(3.6f), Float2Fixed(0.7f),  Float2Fixed(3.5f), Float2Fixed(0.7f),

	Float2Fixed(3.6f), Float2Fixed(0.0f),  Float2Fixed(3.9f), Float2Fixed(0.0f),  Float2Fixed(3.9f), Float2Fixed(0.1f),
	Float2Fixed(3.6f), Float2Fixed(0.0f),  Float2Fixed(3.9f), Float2Fixed(0.1f),  Float2Fixed(3.6f), Float2Fixed(0.1f)
};


#define FIXED(var) (var)
#define RED   FIXED(1.0f) , FIXED(0.0f) , FIXED(0.0f) , FIXED(1.0f)
#define GREEN FIXED(0.0f) , FIXED(1.0f) , FIXED(0.0f) , FIXED(1.0f)
#define BLUE  FIXED(0.0f) , FIXED(0.0f) , FIXED(1.0f) , FIXED(1.0f)

float helloColor[] = {	// HELLO EGL
	// H
	// 0.0,0.0, 0.1,0.0, 0.1,0.7,
	RED,          GREEN,       BLUE,
	// 0.0,0.0, 0.1,0.7, 0.0,0.7,
	RED,          GREEN,       BLUE,

	// 0.1,0.3, 0.3,0.3, 0.3,0.4,
	RED,          GREEN,       BLUE,
	// 0.1,0.3, 0.3,0.4, 0.1,0.4,
	RED,          GREEN,       BLUE,

	// 0.3,0.0, 0.4,0.0, 0.4,0.7,
	RED,          GREEN,       BLUE,
    // 0.3,0.0, 0.4,0.7, 0.3,0.7,
	RED,          GREEN,       BLUE,

	// E
	//0.5,0.0, 0.6,0.0, 0.6,0.7,
	RED,          GREEN,       BLUE,
	//0.5,0.0, 0.6,0.7, 0.5,0.7,
	RED,          GREEN,       BLUE,

	//0.6,0.0, 0.9,0.0, 0.9,0.1,
	RED,          GREEN,       BLUE,
	//0.6,0.0, 0.9,0.1, 0.6,0.1,
	RED,          GREEN,       BLUE,

	//0.6,0.3, 0.9,0.3, 0.9,0.4,
	RED,          GREEN,       BLUE,
	//0.6,0.3, 0.9,0.4, 0.6,0.4,
	RED,          GREEN,       BLUE,

	//0.6,0.6, 0.9,0.6, 0.9,0.7,
	RED,          GREEN,       BLUE,
	//0.6,0.6, 0.9,0.7, 0.6,0.7,
	RED,          GREEN,       BLUE,

	// L
	//1.0,0.0, 1.1,0.0, 1.1,0.7,
	RED,          GREEN,       BLUE,
	//1.0,0.0, 1.1,0.7, 1.0,0.7,
	RED,          GREEN,       BLUE,

	RED,          GREEN,       BLUE,
	//1.1,0.0, 1.4,0.0, 1.4,0.1,
	RED,          GREEN,       BLUE,
	//1.1,0.0, 1.4,0.1, 1.1,0.1,

	// L
	//1.5,0.0, 1.6,0.0, 1.6,0.7,
	RED,          GREEN,       BLUE,
	//1.5,0.0, 1.6,0.7, 1.5,0.7,
	RED,          GREEN,       BLUE,

	//1.6,0.0, 1.9,0.0, 1.9,0.1,
	RED,          GREEN,       BLUE,
	//1.6,0.0, 1.9,0.1, 1.6,0.1,
	RED,          GREEN,       BLUE,

	// O
	//2.0,0.0, 2.1,0.0, 2.1,0.7,
	RED,          GREEN,       BLUE,
	//2.0,0.0, 2.1,0.7, 2.0,0.7,
	RED,          GREEN,       BLUE,

	//2.1,0.0, 2.3,0.0, 2.3,0.1,
	RED,          GREEN,       BLUE,
	//2.1,0.0, 2.3,0.1, 2.1,0.1,
	RED,          GREEN,       BLUE,

	//2.1,0.6, 2.3,0.6, 2.3,0.7,
	RED,          GREEN,       BLUE,
	//2.1,0.6, 2.3,0.7, 2.1,0.7,
	RED,          GREEN,       BLUE,

	//2.3,0.0, 2.4,0.0, 2.4,0.7,
	RED,          GREEN,       BLUE,
	//2.3,0.0, 2.4,0.7, 2.3,0.7,
	RED,          GREEN,       BLUE,

	// E
	//2.5,0.0, 2.6,0.0, 2.6,0.7,
	RED,          GREEN,       BLUE,
	//2.5,0.0, 2.6,0.7, 2.5,0.7,
	RED,          GREEN,       BLUE,

	//2.6,0.0, 2.9,0.0, 2.9,0.1,
	RED,          GREEN,       BLUE,
	//2.6,0.0, 2.9,0.1, 2.6,0.1,
	RED,          GREEN,       BLUE,

	//2.6,0.3, 2.9,0.3, 2.9,0.4,
	RED,          GREEN,       BLUE,
	//2.6,0.3, 2.9,0.4, 2.6,0.4,
	RED,          GREEN,       BLUE,

	//2.6,0.6, 2.9,0.6, 2.9,0.7,
	RED,          GREEN,       BLUE,
	//2.6,0.6, 2.9,0.7, 2.6,0.7,
	RED,          GREEN,       BLUE,

	// G
	//3.1,0.6, 3.4,0.6, 3.4,0.7,
	RED,          GREEN,       BLUE,
	//3.1,0.6, 3.4,0.7, 3.1,0.7,
	RED,          GREEN,       BLUE,

	//3.0,0.0, 3.1,0.0, 3.1,0.7,
	RED,          GREEN,       BLUE,
	//3.0,0.0, 3.1,0.7, 3.0,0.7,
	RED,          GREEN,       BLUE,

	//3.1,0.0, 3.4,0.0, 3.4,0.1,
	RED,          GREEN,       BLUE,
	//3.1,0.0, 3.4,0.1, 3.1,0.1,
	RED,          GREEN,       BLUE,

	//3.3,0.1, 3.4,0.1, 3.4,0.3
	RED,          GREEN,       BLUE,
	//3.3,0.1, 3.4,0.3, 3.3,0.3
	RED,          GREEN,       BLUE,

	//3.25,0.3, 3.45,0.3, 3.45,0.4,
	RED,          GREEN,       BLUE,
	//3.25,0.3, 3.45,0.4, 3.25,0.4,
	RED,          GREEN,       BLUE,

	// L
	//3.5,0.0, 3.6,0.0, 3.6,0.7,
	RED,          GREEN,       BLUE,
	//3.5,0.0, 3.6,0.7, 3.5,0.7,
	RED,          GREEN,       BLUE,

	//3.6,0.0, 3.9,0.0, 3.9,0.1,
	RED,          GREEN,       BLUE,
	//3.6,0.0, 3.9,0.1, 3.6,0.1
	RED,          GREEN,       BLUE
};


#endif // _HELLO_MESH_
